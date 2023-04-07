import vueConfig from '../../vue.config'
import store from '../store'
import axios from 'axios'
import * as pService from '@/utils/pService.js'

const BASE_URL = vueConfig.devServer.proxy['/blocket'].target + "/api"
const INFO_URL = BASE_URL + "/recruit/personalinfo"
const FILE_URL = BASE_URL + "/recruit/Gallery"


export async function getFinalEducation() {
  let result = ''
  let pid = ''
  let uid = ''
  // 신상정보 PK 가져오기.
  await pService.getMyInfo().then(res => {
    pid = res.id
    uid = res.user.id
  })
  
  await axios.get(INFO_URL + "/" + pid + "/myFinalEducation", {
    headers:{
      Authorization:"Bearer "+ store.state.user.accessToken
    }
  })
  .then(res => {
    // 데이터가 없으면 신상정보 PK와 유저 PK만 반환한다.
    if (res.data.length === 0) {
      result = {
        grades: '',
        id: '',
        name: '',
        sortation: '',
        personalinfo: {
          id: pid,
          user: {
            id: uid,
          }
        },
      }
    }
    else {
      result = res.data[0] // 위와 달리 학력에 대한 정보가 포함되어 있다.
    }
  })
  return result
}


export async function createFinalEducation(input, uid, pid, galleryDto, file) {
  let result = ''
  console.log("최종학력 등록합니다.")
  const temp = {
    "grades": input.grades,
    "userId": uid,
    "name": input.name,
    "sortation": input.sortation,
  }
  await axios({
    url: INFO_URL + "/" + pid + "/finaleducation",
    method: "POST", 
    headers: {
      Authorization: "Bearer "+ store.state.user.accessToken,
      'Content-Type': 'application/json'
    },
    data: temp,
  })
  .then(res => {
    
    console.log("생성함!!", res)
    // 생성한 객체를 받아온다.
    result = res.data
    // gallery 테이블에 데이터를 저장해준다.
    galleryDto.sid = result.id
    
    axios({
      url: FILE_URL + "/saveInDB",
      method: "POST",
      headers: {
        'Content-Type': 'application/json'
      },
      data: galleryDto,
    })
    .then(res => {
      console.log("gallery 테이블 저장 결과", res)     
      // 여기서 받아온 Gallery의 PK를 통해 파일을 최종적으로 업로드한다.
      axios({
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


export async function updateFinalEducation(input, pid, sid, galleryDto, file) {  // 이 때 sid는 최종학력 PK를 뜻한다.
  let result = ''
  console.log("최종학력 수정합니다.")
  const temp = {
    "grades": input.grades,
    "name": input.name,
    "sortation": input.sortation,
  }
  await axios({
    url: INFO_URL + "/" + pid + "/" + sid + "/update",
    method: "PUT", 
    headers: {
      Authorization: "Bearer "+ store.state.user.accessToken,
      'Content-Type': 'application/json'
    },
    data: temp,
  })
  .then(async res => {
    result = res.data  // final_Education의 PK 포함.
    // 수정했으면, 파일도 수정한다. 정확히는 기존에 있는 Verif, Gallery를 삭제하고 재등록
    await axios({
      url: FILE_URL + "/" + pid + "/" + sid + "/edu/deleteGallery",
      method: "DELETE",
      headers: {
        Authorization: "Bearer "+ store.state.user.accessToken,
      }
    })
    .then(async res => {
      // 정상적으로 된 경우, Verif와 Gallery가 삭제되고 삭제된 Gallery의 PK를 반환한다. 
      console.log(res)
      // 다시 gallery와 파일, Verif를 등록한다.
      galleryDto.sid = sid
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
  })
  return result
}


export async function findEduVerif(pid, id) {
  let result = ''
  
  await axios({
    url: INFO_URL + "/" + pid + "/" + id + "/findEduVerif",
    method: "POST",
    headers: {
      Authorization:"Bearer "+ store.state.user.accessToken,
    }
  })
  .then(res => {
    console.log("최종학력에 대한 검증 내용은 다음과 같습니다.", res)
    result = res.data
  })
  return result
}