import axios from 'axios'

// 공공 API 키
const OPEN_API_KEY = "da160cfc77fdca28c0a22ecda1813cbe"
const API_URL = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=" + OPEN_API_KEY + "&svcType=api&svcCode=SCHOOL&contentType=json&gubun=univ_list&perPage=500"
const MAJOR_URL = "https://www.career.go.kr/cnet/openapi/getOpenApi?apiKey=" + OPEN_API_KEY + "&svcType=api&svcCode=MAJOR&contentType=json&gubun=univ_list&perPage=5000"

// 모든 대학 목록 반환
export function getAllColleges() {
  return axios.get(API_URL)
}

// 모든 학과 목록 반환
export function getAllMajors() {
  return axios.get(MAJOR_URL)
}

// 대학목록
export function searchCollege(searchSchulNm) {
  return axios.get(API_URL + "&searchSchulNm=" +searchSchulNm).then(res => res.dataSearch.content)
}

// 학과 목록
export function searchMajores(searchTitle) {

  return axios.get(MAJOR_URL + "&searchTitle=" + searchTitle).then(res => res.dataSearch.content)
}
