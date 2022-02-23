#include<iostream>
#include <stack>
using namespace std;
stack<int> myout;
struct node {
	int number;
	int state;
	int rank;
	node* next;
	node* next_order;
	node* pre_oreder;
};

int FIND(node* graph, int x) {
	while (graph[x].number != x) x = graph[x].number;
	return x;
}

void JOIN(node* graph, int fx, int fy) {//前面是新的,输入的是最高结点
	if (fx == fy) return;
	else {
		if (graph[fx].rank > graph[fy].rank) {
			graph[fy].number = fx;
		}
		else {
			if (graph[fx].rank == graph[fy].rank) graph[fy].rank++;
			graph[fx].number = fy;
		}
		return;
	}
}

int ADD_STAR(int star_number, node* graph, int number_of_stars) {
	graph[star_number].state = 1;
	int j, flag = -1, fanhui = 1, temp;
	node* p = &graph[star_number];
	while (p->next != NULL) {
		p = p->next;
		if (graph[p->number].state != -1) {
			flag = FIND(graph, star_number);
			temp = FIND(graph, p->number);
			if (flag != temp) {
				JOIN(graph, temp, flag);
				fanhui--;
			}
		}
	}
	return fanhui;
}

void COMPUTE_IN_ORDER(node* graph, node* tail, int number_of_stars, int number_of_stars_to_destroy) {
	node* p = tail;
	int i = 0, component = 0;
	while (p != NULL) {
		if (i >= number_of_stars - number_of_stars_to_destroy) {
			myout.push(component);
		}
		component += ADD_STAR(p->number, graph, number_of_stars);
		i++;
		p = p->pre_oreder;
	}
	myout.push(component);
}

void OUTPUT() {
	while (!myout.empty()) {
		cout << myout.top() << endl;
		myout.pop();
	}
	return;
}

void INPUT_STARS(node* graph, int number_of_stars, int number_of_ways) {
	int i, a, b;
	graph[0].number = 0;
	graph[0].state = -1;
	graph[0].rank = 1;
	graph[0].next = NULL;
	graph[0].next_order = &graph[1];
	graph[0].pre_oreder = NULL;
	for (i = 1; i < number_of_stars - 1; i++) {
		graph[i].number = i;
		graph[i].state = -1;
		graph[i].rank = 1;
		graph[i].next = NULL;
		graph[i].next_order = &graph[i + 1];
		graph[i].pre_oreder = &graph[i - 1];
	}
	graph[i].number = i;
	graph[i].state = -1;
	graph[i].rank = 1;
	graph[i].next = NULL;
	graph[i].next_order = NULL;
	graph[i].pre_oreder = &graph[i - 1];
	for (i = 0; i < number_of_ways; i++) {
		node* newnode1 = new node, * p;
		cin >> a;
		cin >> b;
		newnode1->number = b;
		newnode1->next = NULL;
		p = &graph[a];
		while (p->next != NULL) p = p->next;
		p->next = newnode1;
		node* newnode2 = new node;
		newnode2->number = a;
		newnode2->next = NULL;
		p = &graph[b];
		while (p->next != NULL) p = p->next;
		p->next = newnode2;
	}
	return;
}

node* INPUT_DESTRUCTION(int number_of_stars, int number_of_stars_to_destroy, node* graph) {
	int i, temp;
	node* tail = &graph[number_of_stars - 1];
	node* head = &graph[0];
	for (i = 0; i < number_of_stars_to_destroy; i++) {
		cin >> temp;
		if (&graph[temp] != tail && &graph[temp] != head) {
			graph[temp].next_order->pre_oreder = graph[temp].pre_oreder;
			graph[temp].pre_oreder->next_order = graph[temp].next_order;
		}
		else if (&graph[temp] == tail && tail != head) {
			graph[temp].pre_oreder->next_order = NULL;
			tail = graph[temp].pre_oreder;
		}
		else if ((&graph[temp] == head)) {
			head = head->next_order;
			continue;
		}
		if (&graph[temp] != head) {
			if (i != 0) {
				head->pre_oreder->next_order = &graph[temp];
			}
			graph[temp].pre_oreder = head->pre_oreder;
			graph[temp].next_order = head;
			head->pre_oreder = &graph[temp];
		}
	}
	return tail;
}

int main() {
	int number_of_stars, number_of_ways, number_of_stars_to_destroy;
	cin >> number_of_stars >> number_of_ways;
	node* graph = new node[number_of_stars];
	INPUT_STARS(graph, number_of_stars, number_of_ways);
	cin >> number_of_stars_to_destroy;
	COMPUTE_IN_ORDER(graph, INPUT_DESTRUCTION(number_of_stars, number_of_stars_to_destroy, graph), number_of_stars, number_of_stars_to_destroy);
	OUTPUT();
	return 0;
}
