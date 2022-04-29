<template>
  <div id="app">
    <router-view></router-view>
  </div>
</template>

<style>
body{
  margin: 0;
  padding: 0;
}
body{
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
  background-size:cover;
  background-image: url('./assets/images/background.jpg');
  background-attachment: fixed;
  background-repeat:no-repeat;
}
#app{
  display: flex;
  flex-direction: column;
  align-items: center;
  width:100%;
}
</style>
<script>
</script>
