import vueConfig from '../../vue.config'
import store from '../store'
import axios from 'axios'

import * as pService from '@/utils/pService.js'

const BASE_URL = vueConfig.devServer.proxy['/blocket'].target + "/api"
const INFO_URL = BASE_URL + "/recruit/personalinfo"
const FILE_URL = BASE_URL + "/recruit/Gallery"

// 활동사항 가져오기. 
export async function getActivities() {
  let result = ''
  let pid = ''
  let uid = ''

  // 가장 먼저 pid를 불러온다.
  await pService.getMyInfo().then(res => {
    pid = res.id
    uid = res.user.id
  })

  await axios.get(INFO_URL + "/" + pid + "/myActivity", {
    headers: {
      Authorization:"Bearer "+ store.state.user.accessToken
    }
  })
  .then(res => {
    // 데이터가 없는 경우에 res.data = []와 같이 나온다. 이 경우, 데이터가 있는 경우와 맞춰서 줘야 한다.
    if (res.data.length === 0) {
      result = {
        data: 'NoData',
        uid: uid,
        pid: pid,
      }
    }
    else {
      result = res.data
    }
  })
  return result
}

// 활동사항 등록하기
export async function createActivity(input, pid, uid, galleryDto, file) {
  let result = ''
  // 유저 ID 추가하기
  input.userId = uid
  await axios({
    url: INFO_URL + "/" + pid + "/activity",
    method: "POST",
    headers: {
      Authorization:"Bearer "+ store.state.user.accessToken,
      'Content-Type': 'application/json',
    },
    data: input
    // 등록한 후, 모든 목록을 반환한다.
  }).then(async res => {
    result = res

    console.log(res.data)
    // res.data[res.data.length - 1]의 가장 마지막 항목이 새로 추가된 항목이다.
    
    // GalleryDto 등록한다.
    galleryDto.sid = res.data[res.data.length -1].id
    await axios({
      url: FILE_URL + "/saveInDB",
      method: "POST",
      headers: {
        'Content-Type': 'application/json'
      },
      data: galleryDto,
    })
    .then(async res => {
      console.log("gallery 테이블 저장 결과", res)     
      // 여기서 받아온 Gallery의 PK를 통해 파일을 최종적으로 업로드한다.
      await axios({
        url: FILE_URL + "/" + res.data.id + "/S3Upload",
        method: "POST",
        data: file
      })
      .then(res => { 
        console.log(res)
      })
    })
  })
  return result
}

// 활동사항 삭제하기
export async function deleteActivity(pid, id) {
  let result = ''
  await axios({
    url: INFO_URL + "/" + pid + "/" + id + "/ActDelete",
    method: "delete",
    headers: {
      Authorization:"Bearer "+ store.state.user.accessToken,
    }
  })
  .then(res => {
    // 데이터가 없는 경우
    if (res.data.length === 0) {
      result = {
        data: 'NoData',
        uid: null,
        pid: null,
      } 
    }
    // 데이터가 하나 이상 존재하는 경우
    else {
      result = res
    }
  })
  return result
}

export async function findActVerif(pid, aInfo) {
  let result = []
  for (let i = 0; i < aInfo.length; i++) {
    await axios({
      url: INFO_URL + "/" + pid + "/" + aInfo[i].id + "/findActVerif",
      method: "POST",
      headers: {
        Authorization:"Bearer "+ store.state.user.accessToken,
      }
    })
    .then(res => {
      result.push(res.data)
    })
  }
  return result
}