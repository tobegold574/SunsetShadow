<template>
    <view>
        <map
            id="map"
            style="width: 100vw; height: 80vh;"
            :latitude="myMarker?.latitude"
            :longitude="myMarker?.longitude"
            :scale="20"
            :markers="covers"
            @markertap="handleMarkerTap"
            show-location
        >
        </map>
    </view>
    <view>
        <view v-if="!markertap">
            <view class="marker-info-mention">点击标记查看详情</view>
        </view>
        <view v-if="markertap">
            <view class="marker-info-text">用户ID：{{tappedmarker?.user_id}}</view><br>
            <view class="marker-info-text">拍摄时间：{{tappedmarker?.shootingTime}}</view><br>
            <view class="marker-info-text">分享：{{tappedmarker?.description}}</view><br>
        </view>
    </view>
</template>

<script setup lang="ts">
import { ref } from 'vue'
import {onLoad} from '@dcloudio/uni-app'
import {HttpManager} from '@/api/index'
import type {ResponseBody} from '@/types/interface'
import type {Marker} from '@/types/marker'
import {useStatus} from '@/store/status'

const covers=ref<Marker[]>([])
const myMarker=ref<Marker>();
const markertap=ref<boolean>(false)

const status=useStatus()
const user_id=status.id

const tappedmarker=ref<Marker>()

const handleMarkerTap=(e)=>{
    markertap.value=true
    covers.value.forEach(marker=>{
        if(marker.user_id==e.detail.markerId){
            tappedmarker.value=marker
        }
    })
}

async function getCovers(){
    const result=(await HttpManager.getAllMarkers()) as ResponseBody
    if(result.success){
        const markers=result.data as Marker[]
        markers.forEach(marker=>{
            marker.iconPath='/static/mappin.png'
            if(marker.user_id==user_id){
                myMarker.value=marker;
            }
            covers.value.push(marker)
            console.log(marker)
        })
    }
}


onLoad(getCovers)
</script>

<style>
.marker-info-mention{
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 5vh;
    color: #c59244;
}

.marker-info-text{
    border: 1px solid #24f09e;
    border-radius: 10%;
    margin-left: 2vw;
    margin-right: 2vw;
    margin-top: 1vh;
    width: 100vw;
}
</style>

