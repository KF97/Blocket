<template>
  <div>
  <Toast1/>
  <!-- 활동 사항 -->
  <div class="p-col profile">
    <div class="p-grid">
      <div class="p-col-10">
        <span class="header-font">활동 사항</span>
      </div>

      <div class="p-col-2 edit-div">
        <Button icon="pi pi-plus" class="p-button-rounded p-button-text" @click="openActivityModal" />
      </div>
    </div>


    <div class="p-col-12" v-if="state.aInfo === ''">
      입력한 활동사항이 없습니다.
      <span style="color: blue; cursor: pointer; display: inline-block;" @click="state.displayActivityModal = true">
        <strong>새로 입력하기</strong>
      </span>
    </div>

    <div v-else class="p-col" v-for="(act, idx) in state.aInfo" :key="act.id">
      <div class="p-mt-3">
        <div class="p-col-12">
          <strong>활동구분:</strong> {{ act.activity }}
        </div>
        <div class="p-col-12">
          <strong>활동명:</strong> {{ act.name }}
        </div>
        <div class="p-col-12">
          <strong>설명:</strong> {{ act.description }}  
        </div>
        <div class="p-col-12">
          <strong>기간:</strong> {{ act.period }}  
        </div>
        <div class="p-d-flex">
          <div class="p-col-4"></div>
          <div class="p-d-flex p-col-8 p-jc-end" style="text-align: end; margin: auto;">
            <Button label="삭제" class="p-button-raised p-button-info p-button-text" @click="deleteActivity(act.id)" 
            style="margin-right: 10px;" />
            <!-- 검증완료일 때 -->
            <div v-if="state.vInfo_act[idx].currentStatus === '승인완료'">
              <Button label="승인" class="p-button-raised p-button-success p-button-text" @click="goToEtherScan(state.vInfo_act[idx].reasonsRejection)"/>
            </div>
            <div v-else-if="state.vInfo_act[idx].currentStatus === '거절'">
              <Button label="거부" class="p-button-raised p-button-warning p-button-text" />
            </div>
            <div v-else>
              <Button label="승인대기" class="p-button-raised p-button-info p-button-text" />
            </div>
          </div>

          
        </div>
      </div>
    </div>
  </div>

  <!-- 활동 사항 추가 modal 창 -->
  <Dialog header="활동사항 입력" v-model:visible="state.displayActivityModal" :style="{width: '50vw'}" :modal="true">
    <form @submit.prevent="handleSubmit(!v$.$invalid)" class="p-fluid p-grid p-formgrid">
      <div class="p-field p-col-12">
        <label for="activityName" :class="{'p-invalid':v$.input.name.$invalid && submitted}">이름*</label>
        <InputText id="activityName" class="input-text" :class="{'p-invalid':v$.input.name.$invalid && submitted}" 
        v-model="v$.input.name.$model" type="activityName" placeholder="예: 소매관리자" />
        <small v-if="(v$.input.name.$invalid && submitted) || v$.input.name.$pending.$response" class="p-error">
          {{ v$.input.name.required.$message.replace('Value', '이름') }}
        </small>
      </div> 

      <div class="p-field p-col-12">
        <label for="activityType" :class="{'p-invalid':v$.input.activity.$invalid && submitted}">활동 구분*</label>
        <select name="type" id="activityType" class="select" :class="{'p-invalid':v$.input.activity.$invalid && submitted}" 
        v-model="v$.input.activity.$model">
          <option value="정규직">정규직</option>
          <option value="계약직">계약직</option>
          <option value="프로젝트">프로젝트</option>
          <option value="인턴">인턴</option>
          <option value="교육 이수">교육 이수</option>
        </select>
        <small v-if="(v$.input.activity.$invalid && submitted) || v$.input.activity.$pending.$response" class="p-error">
          {{ v$.input.activity.required.$message.replace('Value', '활동구분') }}
        </small>
      </div> 

      <div class="p-field p-col-12">
        <label for="description" :class="{'p-invalid':v$.input.description.$invalid && submitted}">설명*</label>
        <InputText id="description" class="input-text" :class="{'p-invalid':v$.input.description.$invalid && submitted}" 
        v-model="v$.input.description.$model" type="description" placeholder="간단한 설명을 적어 주세요." />
        <small v-if="(v$.input.description.$invalid && submitted) || v$.input.description.$pending.$response" class="p-error">
          {{ v$.input.description.required.$message.replace('Value', '설명') }}
        </small>
      </div>

      <div class="p-field p-col-12">
        <label for="icon" :class="{'p-invalid':v$.startDate.$invalid && submitted}">시작일*</label>
        <Calendar id="icon" class="calendar" :class="{'p-invalid':v$.startDate.$invalid && submitted}" 
        v-model="v$.startDate.$model" :showIcon="true" />
        <small v-if="(v$.startDate.$invalid && submitted) || v$.startDate.$pending.$response" class="p-error">
          {{ v$.startDate.required.$message.replace('Value', '시작일') }}
        </small>
      </div>

      <div class="p-field p-col-12">
        <label for="icon" :class="{'p-invalid':v$.endDate.$invalid && submitted}">종료일*</label>
        <Calendar id="icon" class="calendar" :class="{'p-invalid':v$.endDate.$invalid && submitted}"
        v-model="v$.endDate.$model" :showIcon="true" />
        <small v-if="(v$.endDate.$invalid && submitted) || v$.endDate.$pending.$response" class="p-error">
          {{ v$.endDate.required.$message.replace('Value', '종료일') }}
        </small>
      </div>

      <!-- 관련 서류 제출 -->
      <div class="p-field p-col-12">
        <label for="file" class="for" :class="{'p-invalid':v$.file.$invalid && submitted}">활동 증명서 첨부*</label>
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
import * as aService from '@/utils/activityService.js'
import moment from 'moment';

// vuelidate를 이용한 validataion
import { required } from '@vuelidate/validators'
import { useVuelidate } from '@vuelidate/core'

export default {
  name: 'Activity', 
  setup() {
    // 활동사항 불러오기. 단 이 때 여러개일 수 있다.
    aService.getActivities().then(res => {
      // []로 올 수도 있고, [{...}]로 올 수도 있고, [{...}, {...}]로 올 수도 있다.
      // CASE1. []로 오는 경우 ( 등록된 데이터가 없는 경우 )
      if (res.data === 'NoData') {
        state.pid = res.pid
        state.uid = res.uid
      }
      // CASE2. 최소 1개의 데이터가 존재하는 경우
      else {
        state.pid = res[0].personalinfo.id
        state.uid = res[0].personalinfo.user.id
        state.input.userId = res[0].personalinfo.user.id
        state.aInfo = res

        // 활동사항에 대한 검증 내역
        const setVInfo = async () => {
          await aService.findActVerif(state.pid, state.aInfo)
          .then(res => {
            console.log(res)
            state.vInfo_act = res
          })
        }
        setVInfo()
      }
    })
    
    const state = reactive({
      pid: '',          // 신상정보 PK
      uid: '',          // User PK
      displayActivityModal: false,
      startDate: '',
      endDate: '',
      // 활동 사항
      aInfo: '',
      vInfo_act: 'Nodata',      // 검증 내역
      // 새로 입력한 값을 저장하는 변수
      input: {          
        userId: '', 
        activity: '', 
        description: '',
        name: '',
        period: '',
      },
      galleryDto: {
        title: '',
        filePath: '',
        pid: '',
        sid: '',
        sortation: 'act',
      },
      file: '',
    })

    const rules = {
      input: {
        activity: { required }, 
        description: { required },
        name: { required },
      },
      file : { required },
      startDate: { required },
      endDate: { required },
    }
    const submitted = ref(false)
    const v$ = useVuelidate(rules, state)

    const handleSubmit = (isFormValid) => {
      submitted.value = true
      if (!isFormValid) { 
        return
      }      
      createActivity()
    }
    const createActivity = async () => {
      // 데이터 전처리
      state.startDate = moment(state.startDate).format("YY.MM.DD");
      state.endDate = moment(state.endDate).format("YY.MM.DD");
      console.log(state.startDate+"~"+state.endDate);
      state.input.period = state.startDate+"~"+state.endDate;
      await aService.createActivity(state.input, state.pid, state.uid, state.galleryDto, state.file)
      .then(res => { 
        state.aInfo = res.data
      })
      state.displayActivityModal = false
      alert("활동사항을 저장하였습니다.")
      console.log("활동사항을 저장하였습니다. 현재 목록:", state.aInfo)

      // 검증 내역을 다시 불러온다.
      const setVInfo = async () => {
        await aService.findActVerif(state.pid, state.aInfo)
        .then(res => {
          console.log("활동사항 생성 후 검증 내역 목록을 다시 불러옵니다.", res)
          console.log(res)
          state.vInfo_act = res
        })
      }
      setVInfo()

      // 입력을 완료하였으면, Form 내 값을 모두 초기화 한다.
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
      state.input.activity = ''
      state.input.description = ''
      state.input.name = ''
      state.input.period = ''
      state.startDate = ''
      state.endDate = ''
    }
    
    return {
      state, v$, handleSubmit, submitted, getFileInfo
    }
  },

  methods: {
    openActivityModal() {
      this.state.displayActivityModal = true
    },
    saveActivityModal() {
      if (this.state.isWritten === false) {
        aService.createActivity(this.state.input).then(
          this.$toast.add({severity:'success', summary: '시스템 정보', group: 'center', detail:'활동 사항 등록완료', life: 1000})
        )
      }

      this.state.displayActivityModal = false
    },
    goToEtherScan(url) {
      location.replace("https://ropsten.etherscan.io/tx/" + url)
    },
    deleteActivity(id) {
      aService.deleteActivity(this.state.pid, id).then(res => {
        // 데이터가 없는 경우 res.data는 'NoData'이다.
        if (res.data === 'NoData') {
          this.state.aInfo = ''
        }
        // 데이터가 존재하는 경우
        else {
          this.state.aInfo = res.data
        }
      })
      alert("삭제하였습니다.")
    }
  }
}
</script>

<style scoped>

</style>