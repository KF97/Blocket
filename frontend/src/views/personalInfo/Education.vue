<template>
<div>
  <!-- 최종 학력 -->
  <div class="p-col profile">
    <Toast1/>
    <div class="p-grid">
      <div class="p-col-10">
        <span class="header-font">최종 학력</span>
      </div>

      <div class="p-col-2 edit-div">
        <Button icon="pi pi-pencil" class="p-button-rounded p-button-text" @click="openEducationModal" />
      </div>
    </div>

    <div class="p-col">
      <div class="p-grid">
        <div class="p-col-12">
          <strong>최종 학력</strong> : 
          <div v-if="state.eInfo.sortation !== ''">
            {{ state.eInfo.sortation }}
          </div>
          <div v-else>
            아직 입력되지 않았습니다.
          </div>
        </div>

        <div class="p-col-12">
          <strong>학교명</strong> : 
          <div v-if="state.eInfo.name !== ''">
            {{ state.eInfo.name }}
          </div>
          <div v-else>
            아직 입력되지 않았습니다.
          </div>
        </div>

        <div class="p-col-12">
          <strong>학점</strong> : 
          <div v-if="state.eInfo.grades !== ''">
            {{ state.eInfo.grades }}
          </div>
          <div v-else>
            아직 입력되지 않았습니다.
          </div>
        </div> 
      </div>

      <div class="p-col-12" style="text-align: end;">
        {{ state.vInfo_edu.currentStatus }}
      </div>

      <div v-if="state.eInfo.name === ''">
        <span style="color: blue; cursor: pointer; display: inline-block;" @click="state.displayEducationModal = true">
          <strong>새로 입력하기</strong>
        </span>
      </div>
    </div>
  </div>


    <!-- 학력 사항 Modal 창 -->
  <Dialog header="최종 학력 입력" v-model:visible="state.displayEducationModal" :style="{width: '40vw'}" :modal="true">
    <form class="p-grid" @submit.prevent="handleSubmit(!v$.$invalid)">
      
      <!-- 대학교 이름 검색 -->
      <div class="p-field p-col-6">
        <label for="collegeName" :class="{'p-invalid':v$.sName.$invalid && submitted}">학교명*</label>  
        <AutoComplete id="collegeName" :class="{'p-invalid':v$.sName.$invalid && submitted}" v-model="v$.sName.$model" 
        :suggestions="filteredColleges" @complete="searchCollege($event)" field="schoolName" placeholder="ex) OO대학교, OO대학" />
        <small v-if="(v$.sName.$invalid && submitted) || v$.sName.$pending.$response" class="p-error">
          {{ v$.sName.required.$message.replace('Value', '학교명') }}
        </small>
      </div>
      <!-- 학과 이름 검색 -->
      <div class="p-field p-col-6">
        <label for="majorName" :class="{'p-invalid':v$.mName.$invalid && submitted}">학과명*</label>
        <AutoComplete id="majorName" :class="{'p-invalid':v$.sName.$invalid && submitted}" v-model="v$.mName.$model" 
        :suggestions="filteredMajors" @complete="searchMajor($event)" field="mClass" placeholder="ex) OO학과, OO학" />
        <small v-if="(v$.mName.$invalid && submitted) || v$.mName.$pending.$response" class="p-error">
          {{ v$.mName.required.$message.replace('Value', '학과명') }}
        </small>
      </div>
      <!-- 총 학점 -->
      <div class="p-field p-col-3">
        <label for="grade" :class="{'p-invalid':v$.myG.$invalid && submitted}">취득 학점*</label>
        <InputText id="grade" class="input-text" :class="{'p-invalid':v$.myG.$invalid && submitted}" 
        type="grade" style="width: 80px;" v-model="v$.myG.$model" />
        <small v-if="(v$.myG.$invalid && submitted) || v$.myG.$pending.$response" class="p-error">
          {{ v$.myG.required.$message.replace('Value', '평균학점') }}
        </small>
      </div>
      <!-- 4,3 or 4,5 -->
      <div class="p-field p-col-3">
        <label for="scoreType">학점 기준</label>
        <select name="scoreType" id="scoreType" class="select" v-model="state.tScore">
          <option value="4.3">4.3</option>
          <option value="4.5">4.5</option>
        </select>
      </div>
      <!-- 학교 분류 ex) 고등학교, 대학교, 대학원,,, -->
      <div class="p-field p-col-12">
        <label for="sortation">학교분류*</label>
        <select name="sortation" id="sortation" class="select" v-model="state.input.sortation">
          <option value="고등학교">고등학교</option>
          <option value="대학교">대학교</option>
          <option value="대학원(석사졸)">대학원(석사졸)</option>
          <option value="대학원(박사졸)">대학원(박사졸)</option>
        </select>
      </div>
      
      <!-- 졸업 증명서 첨부 -->
      <div class="p-field p-col-12">
        <label for="file" class="for" :class="{'p-invalid':v$.file.$invalid && submitted}">졸업 증명서 첨부*</label>
        <FileUpload mode="basic" name="demo[]" url="./" accept="image/*" :class="{'p-invalid':v$.file.$invalid && submitted}"
        :customUpload="true" :maxFileSize="10485760" @select="getFileInfo" v-model="v$.file.$model" />
        <small v-if="(v$.file.$invalid && submitted) || v$.file.$pending.$response" class="p-error">
          {{ v$.file.required.$message.replace('Value', 'file') }}
        </small>
      </div>
      <div class="p-col-12">
        <Button type="submit" label="저장" autofocus style="width: 100%;" />
      </div>
      
    </form>  
  </Dialog>
</div>
</template>

<script>  
import { reactive, onMounted, ref } from 'vue'
import { getAllColleges, getAllMajors } from '@/utils/colleges.js'
import { FilterService, FilterMatchMode }  from 'primevue/api'

import * as eService from '@/utils/educationService.js'

// vuelidate를 이용한 validataion
import { required } from '@vuelidate/validators'
import { useVuelidate } from '@vuelidate/core'

export default {
  name: 'Education',
  setup() {
    const colleges = ref()         // 모든 학교
    const filteredColleges = ref() // 검색 결과로 나온 학교들
    const majors = ref()           // 모든 전공
    const filteredMajors = ref()   // 검색 결과로 나온 전공들

    // 최종학력 불러오기
    eService.getFinalEducation().then(res => {
      state.id = res.id
      state.pid = res.personalinfo.id
      state.uid = res.personalinfo.user.id
      state.eInfo.grades = res.grades
      state.eInfo.name = res.name
      state.eInfo.sortation = res.sortation
      state.input.sortation = res.sortation

      state.sName = res.name.split(" ")[0]          // 대학교 검색 시 내가 선택한 학교명. 없으면 undefined
      state.mName = res.name.split(" ")[1]          // 학과 검색 시 내가 선택한 학과명. 없으면 undefined
      state.myG = res.grades.split(" / ")[0]        // 내 학점. 없으면 undefined
      state.tScore = res.grades.split(" / ")[1]     // 학점 기준. 없으면 undefined

      // 최종학력을 불러왔으면, 검증 내역도 함께 불러온다.
      const setVInfo = async () => {
        await eService.findEduVerif(state.pid, state.id)
        .then(res => {
          console.log(res)
          state.vInfo_edu = res
        })
      }
      setVInfo()
    })

    const state = reactive({
      id: '',   // 학력사항 PK. 있는 경우에만 불러온다. (업데이트 위해)
      uid: '',  // 회원 PK
      pid: '',  // 신상정보 PK
      displayEducationModal: false,
      // 이미 작성한 내용이 있는 경우, 불러올 때 사용한다.
      eInfo: {
        grades: '',       // 내가 취득한 평균 학점
        name: '',         // 학교명
        sortation: '',    // 분류.
      },
      vInfo_edu: '',
      // 신규 작성할 내용. 나중에 조립해야 함.
      input: {
        grades: '',
        name: '',
        sortation: '',
      },
      galleryDto: {
        title: '',
        filePath: '',
        pid: '',
        sid: '',
        sortation: 'edu',
      },
      file: '',
      sName: '',
      mName: '',
      myG: '',
      tScore: '',
    })

    const rules = {
      sName: { required },
      mName: { required },
      myG: { required },
      file: { required },
    }

    const submitted = ref(false)
    const v$ = useVuelidate(rules, state)
    
    const handleSubmit = (isFormValid) => {
      submitted.value = true
      if (!isFormValid) {
        return
      }      
      createFinalEducation()
    }

    const createFinalEducation = () => {
      // 기본적으로, 파일이 반드시 선택되어 있어야 한다.

      // 최종 학력 등록하기
      state.input.name = state.sName.schoolName  + " " + state.mName.mClass
      state.input.grades = state.myG + " / " + state.tScore

      // 만약 처음으로 작성하는 거라면...
      if (state.id === '') {
        const temp = async() => {
          await eService.createFinalEducation(state.input, state.uid, state.pid, state.galleryDto, state.file)
          .then(res => {
            // 값 갱신하기
            state.eInfo.grades = res.grades
            state.eInfo.name = res.name
            state.eInfo.sortation = res.sortation

            // 등록한 final_education의 id를 저장해야 한다.
            state.id = res.id

            alert("등록하였습니다.")

            // 검증 내역을 불러온다.
            const setVInfo = async () => {
              await eService.findEduVerif(state.pid, state.id)
              .then(res => {
                
                console.log(res)
                state.vInfo_edu = res
              })
            }
            setVInfo()
          })
        }
        temp()
      }
      // 이미 작성되었다면
      else {
        const temp = async () => {
          await eService.updateFinalEducation(state.input, state.pid, state.id, state.galleryDto, state.file)
          .then(res => {
            // 값 갱신하기
            state.eInfo.grades = res.grades
            state.eInfo.name = res.name
            state.eInfo.sortation = res.sortation
            alert("수정하였습니다.")

            // 업데이트를 하였으면, 다시 검증 내역을 불러온다.
            const setVInfo = async () => {
              await eService.findEduVerif(state.pid, state.id)
              .then(res => {
                console.log("최종 학력을 수정하였습니다. 검증 내역은:", res)
                state.vInfo_edu = res
              })
            }
            setVInfo()
          })
        }
        temp()

      }
      state.displayEducationModal = false
    }

    const getFileInfo = e => {
      const formData = new FormData()
      formData.append("file", e.files[0])

      state.file = formData
      console.log("업로드 할 파일 정보 : ", formData)
      
      // GalleryDto 설정을 해주자.
      state.galleryDto.filePath = e.files[0].name
      state.galleryDto.pid = state.pid
    }

    onMounted(() => {
      // 모든 대학교 목록 불러오기
      getAllColleges().then(res => {
        colleges.value = res.data.dataSearch.content
      })

      // 학과 목록 불러오기
      getAllMajors().then(res => {
        majors.value = res.data.dataSearch.content
      })
    })

    return {
      state,
      colleges,
      filteredColleges,
      majors, 
      filteredMajors,
      v$, handleSubmit, submitted, getFileInfo
    }
  },
  methods: {
    openEducationModal() {
      this.state.displayEducationModal = true
    },
    // 대학교 검색
    searchCollege(event) {
      setTimeout(() => {
        // 검색어가 존재하지 않는 경우
        if (!event.query.trim().length) { // query는 검색어를 의미한다.
          this.filteredColleges.value = [...this.colleges.value]
        }
        else {
          this.filteredColleges = FilterService.filter(this.colleges, ['schoolName'], event.query.trim(), FilterMatchMode.CONTAINS)
          
        }
      })
    },
    // 학과 검색
    searchMajor(event) {
      setTimeout(() => {
        // 검색어가 존재하지 않는 경우
        if (!event.query.trim().length) { 
          this.filteredMajors.value = [...this.majors.value]
        }
        else {
          this.filteredMajors = FilterService.filter(this.majors, ['mClass'], event.query.trim(), FilterMatchMode.CONTAINS)
        }
      })
    },
  }
}

</script>

<style scoped>

</style>