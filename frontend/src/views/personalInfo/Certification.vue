<template>
<div>
  <!-- 어학, 자격증 -->
  <div class="p-col profile">
    <div class="p-grid">
      <div class="p-col-10">
        <span class="header-font">어학, 자격증</span>
      </div>

      <div class="p-col-2 edit-div">
        <Button icon="pi pi-plus" class="p-button-rounded p-button-text" @click="openCertModal" />
      </div>
    </div>

    <div v-if="state.cInfo === ''">
      입력한 자격증이 없습니다.
      <span style="color: blue; cursor: pointer; display: inline-block;" @click="state.displayCertModal = true">
        <strong>새로 입력하기</strong>
      </span>
    </div>

    <div v-else class="p-col" v-for="(cert, idx) in state.cInfo" :key="cert.id" >
      <div class="p-mt-3">
        <div class="p-col-12">
          <strong>구분:</strong> {{ cert.sortation }}
        </div>
        <div class="p-col-12">
          <strong>이름:</strong> {{ cert.name }}
        </div>
        <div class="p-col-12">
          <strong>점수 | 등급:</strong> {{ cert.score }}  
        </div>
        <div class="p-col-12">
          <strong>취득일:</strong> {{ cert.acquisitionDate }}  
        </div>

        <div class="p-d-flex">
          <div class="p-col-4">
            <Button icon="pi pi-times" class="p-button-rounded p-button-text" @click="deleteCertification(cert.id)" />            
          </div>
          <div class="p-col-8" style="text-align: end; margin: auto;">
            <span>{{ state.vInfo_cert[idx].currentStatus }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>

  <Dialog header="어학, 자격증" v-model:visible="state.displayCertModal" :style="{width: '30vw'}" :modal="true">
    <form @submit.prevent="handleSubmit(!v$.$invalid)">
      <div class="p-field">
        <label for="name" :class="{'p-invalid':v$.input.name.$invalid && submitted}">이름*</label>
        <InputText id="name" class="input-text" :class="{'p-invalid':v$.input.name.$invalid && submitted}"
        type="certName" v-model="v$.input.name.$model" placeholder="예: 국제 공인 네트워크 자격증" />
        <small v-if="(v$.input.name.$invalid && submitted) || v$.input.name.$pending.$response" class="p-error">
          {{ v$.input.name.required.$message.replace('Value', '이름') }}
        </small>
      </div>

      <div class="p-field">
        <label for="certType" :class="{'p-invalid':v$.input.sortation.$invalid && submitted}">구분*</label>
        <select name="certType" id="certType" class="select" :class="{'p-invalid':v$.input.sortation.$invalid && submitted}" v-model="v$.input.sortation.$model">
          <option value="1">어학</option>
          <option value="2">국가공인 자격증 </option>
          <option value="3">민간 자격증</option>
          <option value="4">기타</option>
        </select>
        <small v-if="(v$.input.sortation.$invalid && submitted) || v$.input.sortation.$pending.$response" class="p-error">
          {{ v$.input.sortation.required.$message.replace('Value', '구분') }}
        </small>
      </div> 

      <div class="p-fluid p-grid p-formgrid">
        <div class="p-field p-col-12">
          <label for="score" :class="{'p-invalid':v$.input.score.$invalid && submitted}">취득점수, 등급</label>
          <InputText id="score" class="input-text" :class="{'p-invalid':v$.input.score.$invalid && submitted}" type="score" v-model="v$.input.score.$model" />
          <small v-if="(v$.input.score.$invalid && submitted) || v$.input.score.$pending.$response" class="p-error">
            {{ v$.input.score.required.$message.replace('Value', '점수') }}
          </small>
        </div>
        
        <div class="p-field p-col-12">
          <label for="certIcon" :class="{'p-invalid':v$.input.acquisitionDate.$invalid && submitted}">취득기간*</label>
          <Calendar id="certIcon" class="calendar" :class="{'p-invalid':v$.input.acquisitionDate.$invalid && submitted}"
          v-model="v$.input.acquisitionDate.$model" :showIcon="true" />
          <small v-if="(v$.input.acquisitionDate.$invalid && submitted) || v$.input.acquisitionDate.$pending.$response" class="p-error">
            {{ v$.input.acquisitionDate.required.$message.replace('Value', '취득기간') }}
          </small>
        </div>
      </div>

      <!-- 관련 서류 제출 -->
      <div class="p-field p-col-12">
        <label for="file" class="for" :class="{'p-invalid':v$.file.$invalid && submitted}">증명서 첨부*</label>
        <FileUpload mode="basic" name="demo[]" url="./" accept="image/*" :class="{'p-invalid':v$.file.$invalid && submitted}"
        :customUpload="true" :maxFileSize="10485760" @select="getFileInfo" v-model="v$.file.$model" />
      <small v-if="(v$.file.$invalid && submitted) || v$.file.$pending.$response" class="p-error">
        {{ v$.file.required.$message.replace('Value', 'file') }}
      </small>
      </div>
      <div class="p-col-12" style="padding: 0;">
        <Button type="submit" label="저장" autofocus style="width: 100%;" />
      </div>
    </form>
  </Dialog>
</div>
</template>

<script>
import { reactive, ref } from 'vue'
import moment from 'moment';

// vuelidate를 이용한 validataion
import { required } from '@vuelidate/validators'
import { useVuelidate } from '@vuelidate/core'

import * as cService from '@/utils/CertificationService.js'

export default {
  name: 'Certification', 
  setup() {
    // Created
    cService.getCertification().then(res => {
      // 데이터가 한건도 없는 경우
      if (res.data === 'NoData') {
        state.uid = res.uid
        state.pid = res.pid
      }
      // 데이터가 한 건이라도 존재하는 경우
      else {
        state.pid = res[0].personalinfo.id
        state.uid = res[0].personalinfo.user.id
        state.input.userId = res[0].personalinfo.user.id
        state.cInfo = res

        // 활동사항에 대한 검증 내역
        const setVInfo = async () => {
          await cService.findCertVerif(state.pid, state.cInfo)
          .then(res => {
            console.log(res)
            state.vInfo_cert = res
          })
        }
        setVInfo()
      }
    })

    const state = reactive({
      uid: '',
      pid: '',
      displayCertModal: false,
      cInfo: '',
      vInfo_cert: 'Nodata',    // 검증 내역
      // 어학, 자격증
      input: {
        userId: '',
        name: '',
        sortation: '',
        acquisitionDate: '',
        score: '',
      },
      galleryDto: {
        title: '',
        filePath: '',
        pid: '',
        sid: '',
        sortation: 'cert',
      },
      file: '',
    })
    const rules = {
      input: {
        name: { required },
        sortation: { required },
        acquisitionDate: { required },
        score: { required },
      },
      file : { required },
    }

    const submitted = ref(false)
    const v$ = useVuelidate(rules, state)

    const handleSubmit = (isFormValid) => {
      submitted.value = true
      if (!isFormValid) { 
        return
      }      
      createCertification()
    }
    
    const createCertification = async () => {
      state.input.acquisitionDate =  moment(state.input.acquisitionDate).format("YY.MM.DD");
      await cService.createCertification(state.input, state.pid, state.uid, state.galleryDto, state.file).
      then(res => {
        state.cInfo = res
        console.log("자격증 생성하였습니다. 현재 목록:", state.cInfo)
        // 등록한 후에 검증내역도 함께 불러와야 한다.
        // 활동사항에 대한 검증 내역
        const setVInfo = async () => {
          await cService.findCertVerif(state.pid, state.cInfo)
          .then(res => {
            console.log("자격증 생성 후 검증 내역 목록을 다시 불러옵니다.", res)
            console.log(res)
            state.vInfo_cert = res
          })
        }
        setVInfo()
      })
      state.displayCertModal = false
      resetForm()
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

    const resetForm = () => {
      state.input.name = ''
      state.input.sortation = ''
      state.input.acquisitionDate = ''
      state.input.score = ''
    }

    return {
      state, v$, handleSubmit, submitted, getFileInfo
    }
  },
  methods: {
    // ========== 어학, 자격증 ========== //
    openCertModal() {
      this.state.displayCertModal = true
    },
    // 자격증 삭제하기
    deleteCertification(id) {
      cService.deleteCertification(this.state.pid, id).then(res => {
        // 데이터가 없는 경우
        if (res.data === 'NoData') {
          this.state.cInfo = ''
        }
        // 데이터가 존재하는 경우
        else {
          this.state.cInfo = res.data
        }
      })
      alert("삭제하였습니다.")
    }
  }
}
</script>

<style scoped>

</style>