import vueConfig from '../../vue.config'
import store from '../store'
import axios from 'axios'

import * as pService from '@/utils/pService.js'

const BASE_URL = vueConfig.devServer.proxy['/blocket'].target + "/api"
const INFO_URL = BASE_URL + "/recruit/personalinfo"
const FILE_URL = BASE_URL + "/recruit/Gallery"

export async function getCertification() {
  let result = ''
  let uid = ''
  let pid = ''

  // 신상정보 PK, UID 가져오기
  await pService.getMyInfo().then(res => {
    pid = res.id
    uid = res.user.id
  })
  // 작성한 어학, 자격증 사항 모두 불러오기
  await axios({
    url: INFO_URL + "/" + pid + "/myCertificate",
    method: "GET",
    headers: {
      Authorization: "Bearer "+ store.state.user.accessToken,
      'Content-Type': 'application/json',
    }
  })
  .then(res => {
    // 등록된 데이터가 없는 경우
    if (res.data.length === 0) {
      result = {
        data: 'NoData',
        uid: uid,
        pid: pid,
      }
    }
    // 등록한 데이터가 1개 이상 존재하는 경우
    else {
      result = res.data
    }
  })
  return result
}


export async function createCertification(input, pid, uid, galleryDto, file) {
  let result = ''
  input.userId = uid
  await axios({
    url: INFO_URL + "/" + pid + "/certificate",
    method: "POST",
    headers: {
      Authorization: "Bearer "+ store.state.user.accessToken,
      'Content-Type': 'application/json',
    },
    data: input
  })
  .then(async res => {
    result = res.data

    // res.data[res.data.length -1] 이 가장 마지막에 추가된 항목이다.

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


export async function deleteCertification(pid, id) {
  let result = ''
  await axios({
    url: INFO_URL + "/" + pid + "/" + id + "/CertDelete", 
    method: "DELETE",
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


export async function findCertVerif(pid, cInfo) {
  let result = []
  for (let i = 0; i < cInfo.length; i++) {
    await axios({
      url: INFO_URL + "/" + pid + "/" + cInfo[i].id + "/findCertVerif",
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