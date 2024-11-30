<script setup lang="ts">
import { onLaunch, onShow, onHide } from "@dcloudio/uni-app";
onLaunch(() => {
  console.log("App Launch");
});
onShow(() => {
  console.log("App Show");
});
onHide(() => {
  console.log("App Hide");
});
</script>
<style>
.bg-img{
    position: fixed;
    object-fit: cover; 
    overflow: hidden;
    width: 100vw;
    height: 100vh;
    z-index: -1;
}
.avatar{
  border-radius: 100%;
  width:25vw;
  height:25vw;
  border: 1px solid #ce1919;
}
.horizontal-divider{
    width: 100%;
    height: 1px;
    background-color: #000;
    margin-top: 1vh;
    margin-bottom: 1vh;
  }
  .space{
    margin-top: 2vh;
    margin-bottom: 2vh;
    display:flex;
    align-items: center;
    justify-content: center;
  }
</style>
