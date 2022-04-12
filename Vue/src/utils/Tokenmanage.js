import request from "@/utils/request";
let tokenmanage={
    set(key, value){
        let obj = {
            data: value,
            time: Date.now(),
            expire: 3600000 //一个小时
        };
        if(this.get(key)!==null){
            this.remove(key);
        }
        localStorage.setItem(key, JSON.stringify(obj));
    },
    get(key){
        let val = localStorage.getItem(key);
        if (!val) {
            return val;
        }
        val = JSON.parse(val);
        if (Date.now() - val.time > val.expire) {
            localStorage.removeItem(key);
            request.post("/logout")
                .then(function(res){});
            return "0";
        }
        return val.data;
    },
    remove(key) {
        localStorage.removeItem(key);
    },
}
export default tokenmanage;

