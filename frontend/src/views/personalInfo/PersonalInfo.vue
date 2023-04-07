<template>
<div>
  <Toast1/>
  <div style="background-color: #F9F7F7;">
    <!-- container -->
    <div class="p-grid p-jc-center"> <!-- 내부 요소를 가운데 정렬한다. -->
      <div class="p-col-6 p-flex-column"> <!-- 내부 요소의 너비를 col-4로 지정 -->
        
        <!-- 사용자 프로필 -->
        <div class="p-col profile" style="padding: 0px; height: 430px;">
          <!-- 배경 이미지 부분 -->
          <img class="bg-img" :src="state.defaultImage" alt="">
          <!-- 사용자 프로필 이미지 부분 -->
          <div class="user-img-frame" style="overflow: hidden;">
            <div class="sub-frame">
              <img class="user-img" :src="state.propImage" alt="" width="100px" height="100px" style="border-radius: 100px; object-fit:cover;">
            </div>
          </div>
          <!-- 사용자 프로필 이미지 수정 버튼 -->
          <div class="user-img-mod-frame">
            <div class="mod-sub-frame">
              <label for="ex_file">
                <i class="pi pi-pencil"></i>
              </label>
              <input type="file" id="ex_file" @change="propUpdate"/>
            </div>
          </div>
          <!-- 인적 사항 부분 -->
          <div class="p-grid user-info" style="margin-top:50px;">
            
            <div class="p-col-4">
              <div style="font-size: 30px;">{{ state.user.name }}</div>
              <div><h5> 이메일 : {{ state.user.email }}</h5></div>
              <div><h5> 소속 : {{ state.user.belong }}</h5></div>
            </div>
            <div class="p-col-7 p-text-right">
              <span style="cursor: pointer; font-weight: bold; color: #3F72AF;" @click="goToModify">수정하기</span>
            </div>
          </div>
        </div> <!-- end of 사용자 프로필 -->
        

        <!-- 생년월일, 주소, 영문이름, 성별 기재 -->
        <div class="p-col profile">
          <div class="p-grid">
            <div class="p-col-10">
              <span class="header-font">기본정보</span>
            </div>
            <div class="p-col-2 edit-div">
              <Button icon="pi pi-pencil" class="p-button-rounded p-button-text" @click="openInfoModal" />
            </div>
          </div>
          <div class="p-ml-3" >
            <div class="p-mb-2"><strong>성별</strong>
             <div v-if="state.personalInfo.gender !== null">
               {{ state.personalInfo.gender }}
             </div>
             <div v-else>
               입력사항 없음
             </div>
            </div>

            <div class="p-mb-2"><strong>영문 이름</strong> 
              <div v-if="state.personalInfo.englishName !== null">
                {{ state.personalInfo.englishName }}
              </div>
              <div v-else>
                입력사항 없음
              </div>
            </div>

            <div class="p-mb-2"><strong>생년월일</strong>
              <div v-if="state.personalInfo.dateBirth !== null">
                {{ state.personalInfo.dateBirth }}
              </div>
              <div v-else>
                입력사항 없음
              </div>
            </div>

            <div class="p-mb-2"><strong>주소</strong>
              <div v-if="state.personalInfo.address !== null">
                {{ state.personalInfo.address }}
              </div>
              <div v-else>
                입력사항 없음
              </div>
            </div>

          </div>
        </div> 

        <Education />

        <Activity />

        <Certification />

        <!-- 병역사항 기재 -->
        <div class="p-col profile">
          <div class="p-grid">
            <div class="p-col-10">
              <span class="header-font">병역 사항</span>
            </div>

            <div class="p-col-2 edit-div">
              <Button icon="pi pi-plus" class="p-button-rounded p-button-text" @click="openArmyModal" />
            </div>
          </div>
          <div class="p-col" v-if="state.checkarmy">
            <div class="p-mb-2"><strong>구분 : </strong> {{state.checkarmy}} </div>
            <div class="p-mb-2"><strong>군별 : </strong> {{state.kind}} </div>
            <div class="p-mb-2"><strong>전역 사유 : </strong> {{state.discharge}} </div>
            <div class="p-mb-2"><strong>복무 기간 : </strong> {{state.start}} <span v-if="state.start">~</span> {{state.end}}</div>
          </div>
          <div class="p-col" v-else>병역 사항을 입력해주세요.</div>
        </div>

        <!-- 장애인 여부 기재 -->
        <div class="p-col profile">
          <div class="p-grid">
            <div class="p-col-10">
              <span class="header-font">장애 여부</span>
            </div>
            <div class="p-col-2 edit-div">
              <Button icon="pi pi-plus" class="p-button-rounded p-button-text" @click="openDisabledModal" />
            </div>
          </div>
            <div v-if="state.personalInfo.disabled">
            <div class="p-col" v-if="state.Disabled_check">
              <div class="p-mb-2"><strong>여부 : </strong> {{state.personalInfo.disabled}} </div>
            <div class="p-mb-2"><strong>사유 : </strong> {{state.description}} </div>
            </div>
            <div class="p-col" v-else>
              <div class="p-mb-2"><strong>여부 : </strong> {{state.personalInfo.disabled}} </div>
            </div>
          </div>
          <div class="p-col" v-else>장애 여부를 입력해주세요.</div>
        </div>


      </div> <!-- end of vertical container -->
    </div> <!-- end of sub Container -->
  </div> <!-- end of top Container -->

  <!-- ============================== Modal ============================== -->

  <!-- 신상정보 Modal 창 -->
  <Dialog header="기본 정보 수정" v-model:visible="state.displayInfoModal" :style="{width: '40vw'}" :modal="true">    
    <form @submit.prevent="handleSubmit(!v$.$invalid)" class="p-fluid p-grid p-formgrid">
      <div class="p-field p-col-12">
        <label for="gender">성별</label>
        <select name="gender" id="gender" class="select" v-model="state.input.gender">
          <option value="남성">남성</option>
          <option value="여성">여성</option>
        </select>
      </div>

      <div class="p-field p-col-12">
        <label for="englishName" :class="{'p-invalid':v$.input.englishName.$invalid && submitted}">영문이름*</label>
        <InputText id="englishName" class="input-text" :class="{'p-invalid':v$.input.englishName.$invalid && submitted}" 
        type="englishName" style="width: 100%;" v-model="v$.input.englishName.$model" placeholder="ex) SSAFY, Kim" />
        <small v-if="(v$.input.englishName.$invalid && submitted) || v$.input.englishName.$pending.$response" class="p-error">
          {{ v$.input.englishName.required.$message.replace('Value', 'english Name') }}
        </small>
      </div>
      

      <div class="p-field p-col-12">
        <label for="dateBirth" :class="{'p-invalid':v$.input.dateBirth.$invalid && submitted}">생년월일*</label>
        <Calendar id="dateBirth" class="calendar" :class="{'p-invalid':v$.input.dateBirth.$invalid && submitted}" 
        v-model="v$.input.dateBirth.$model" :showIcon="true" />
        <small v-if="(v$.input.dateBirth.$invalid && submitted) || v$.input.dateBirth.$pending.$response" class="p-error">
          {{ v$.input.dateBirth.required.$message.replace('Value', 'date Birth') }}
        </small>
      </div>

      <div class="p-field p-col-12">
        <label for="address" :class="{'p-error':v$.input.address.$invalid && submitted}">주소*</label>
        <InputText id="address" class="input-text" :class="{'p-invalid':v$.input.address.$invalid && submitted}" 
        type="address" style="width: 100%;" v-model="v$.input.address.$model" />
        <small v-if="(v$.input.address.$invalid && submitted) || v$.input.address.$pending.$response" class="p-error">
          {{ v$.input.address.required.$message.replace('Value', 'address') }}
        </small>
      </div>
      <Button label="저장" type="submit" autofocus />
    </form>  
  </Dialog>

  <!-- 병역 사항 관련 Modal창 -->
  <Dialog header="병역 사항 수정" v-model:visible="state.displayArmyModal" :style="{width: '30vw'}" :modal="true">
    <div class="p-field">
      <label for="armyType">병역 여부*</label>
      <select name="armyType" id="armyType" class="select" v-model="state.checkarmy">
        <option value="군필">군필</option>
        <option value="미필">미필</option>
      </select>
    </div> 

    <div class="p-field">
      <label for="armyType2">군종*</label>
      <select name="armyType2" id="armyType2" class="select" v-model="state.kind">
        <option value="육군">육군</option>
        <option value="해군">해군</option>
        <option value="공군">공군</option>
        <option value="해병대">해병대</option>
        <option value="의경">의경</option>
      </select>
    </div>

    <div class="p-field">
      <label for="armyType3">제대 종류*</label>
      <select name="armyType3" id="armyType3" class="select" v-model="state.discharge">
        <option value="만기전역">만기전역</option>
        <option value="의가사 전역">의가사 전역</option>
        <option value="기타">기타</option>
      </select>
    </div>
    
    <div class="p-fluid p-grid p-formgrid">
      <div class="p-field p-col-6 p-md-6">
        <label for="certIcon">입대일*</label>
        <Calendar id="certIcon" class="calendar" v-model="state.start" :showIcon="true" />
        <!-- Error msg 출력 -->
        <div></div>
      </div>

      <div class="p-field p-col-6 p-md-6">
        <label for="certIcon">전역일*</label>
        <Calendar id="certIcon" class="calendar" v-model="state.end" :showIcon="true" />
        <!-- Error msg 출력 -->
        <div></div>
      </div>
    </div>

    <template #footer>
      <Button label="저장" icon="pi pi-check" @click="saveArmyModal" autofocus />
    </template>
  </Dialog>

  <!-- 장애 여부 관련 Modal 창-->
  <Dialog header="장애 여부 수정" v-model:visible="state.displayDisabledModal" :style="{width: '30vw'}" :modal="true">
    <div class="p-field">
      <label for="disabledType">장애 여부*</label>
      <select name="disabledType" id="disabledType" @change="change($event)" class="select" v-model="state.personalInfo.disabled">
        <option value="있음">있음</option>
        <option value="없음">없음</option>
      </select>
    </div> 

    <div class="p-field" v-if="state.Disabled_check">
      <label for="description">설명*</label>
      <InputText v-model="state.description" id="description" class="input-text" type="description" placeholder="설명을 적어 주세요." />
    </div>

    <template #footer>
      <Button label="저장" icon="pi pi-check" @click="saveDisabledModal" autofocus />
    </template>
  </Dialog>

</div>
</template> <!-- end of HTML code -->

<script>
import { reactive, ref } from 'vue'
import router from '../../router'
import defaultImage from "~/images/test.png"
import defaultUserImage from "~/images/user.png"

// utils
import * as pService from '@/utils/pService.js' // default를 붙이면 중괄호 없이 가져올 수 있다..! 반대로 default가 없는 경우에는 중괄호 필수
// 자식 컴포넌트
import Activity from "./Activity.vue"
import Education from "./Education.vue"
import Certification from "./Certification.vue"
import Toast1 from "@/components/Toast.vue";
// vuelidate를 이용한 validataion
import { required } from '@vuelidate/validators'
import { useVuelidate } from '@vuelidate/core'

export default {
  name: 'PersonalInfo',
  components: { Activity, Education, Certification, Toast1 },
  setup() {
    // Created
    // pService.checkToken() // 토큰 정보 확인
    pService.getMyInfo().then(res => {
      
      // 신상정보 PK 저장하기
      state.pid = res.id
      
      // 갤러리에서 사용자 프로필 찾아오기.
      pService.getPropImg(res.id).then(res => {
        
        if (res.data === '등록이미지없음') {
          console.log("등록한 프로필 이미지 없습니다.")
        } else {
          console.log("created in setup():", res.data)
          // 프로필 이미지 경로 재설정해주자.
          state.propImage = 'https://' + 'd11bfjty6ba1yx.cloudfront.net' + '/' + res.data
        }
      })

      // 사용자 이름, 소속, 이메일 설정
      state.user.name = res.user.name
      state.user.belong = res.user.belong
      state.user.email = res.user.email

      // 영문이름, 성별, 생년월일, 주소 설정
      state.personalInfo.englishName = res.englishName
      state.personalInfo.gender = res.gender
      state.personalInfo.address = res.address
      state.personalInfo.dateBirth = res.dateBirth

      // 만약 위의 4가지가 null이 아닌경우에는 input에도 넣어준다.
      if (res.englishName !== null && res.gender !== null && res.address !== null && res.dateBirth !== null) {
        state.input.englishName = res.englishName
        state.input.gender = res.gender
        state.input.address = res.address
        state.input.dateBirth = res.dateBirth
      }
    })
    
    const state = reactive({
      name: null,   // 사용자 이름
      belong: null, // 소속
      defaultUserImage: defaultUserImage,
      description : null,
      Disabled_check : '',
      //병역 사항
      checkarmy : null,
      kind : null,
      discharge : null,
      start : null,
      end : null,
      // Modal창 on, off
      displayArmyModal: false,
      displayDisabledModal : false,

      disabled_fake : '',

      pid: null,
      user: {
        name: '',
        belong: '',
        email: '',
      },
      defaultImage: defaultImage,
      propImage: defaultUserImage,
      // Modal창 on, off
      displayInfoModal: false,
      
      // DB에서 받아올 때 사용.
      personalInfo: {
        englishName: '',
        address: '',
        dateBirth: '',
        gender: '',
        id: null,               // PK
        militaryService: null,  // 병역사항
        veteransAffairs: null,  // 보훈사항
        disabled: null,         // 장애여부

      },
      // 개인 정보. 새로 값을 입력하거나 수정할 때 사용한다.
      input: {
        englishName: '',
        address: '',
        dateBirth: '',
        gender: '',
      },
      propFile: '', // 프로필 이미지 전용
    })
    const rules = {
      input: {
        englishName: { required },
        address: { required },
        dateBirth: { required },
        gender: { required },
      }
    }

    const submitted = ref(false)
    const v$ = useVuelidate(rules, state)

    const handleSubmit = (isFormValid) => {
      submitted.value = true
      if (!isFormValid) {
        return
      }      
      saveInfoModal()
    }

    const propUpdate = e => {
      const formData = new FormData()
      formData.append("file", e.target.files[0])

      state.propFile = formData

      // 바로 업로드 진행하자.
      pService.savePropImg(state.pid, state.propFile).then(res =>{
        // 이때 res는 새로 저장한 이미지의 경로이다.
        alert("프로필을 업데이트하였습니다.", res)
        state.propImage = 'https://' + 'd11bfjty6ba1yx.cloudfront.net' + '/' + res

        
      })
    }

    const saveInfoModal = () => {
      // 신상정보 변경사항 저장. (영문이름, 성별, 주소, 생년월일)
      pService.saveInfoModal(state.pid, state.input).then(res => {
        // 다시 불러오기
        state.personalInfo.englishName = res.englishName
        state.personalInfo.gender = res.gender
        state.personalInfo.address = res.address
        state.personalInfo.dateBirth = res.dateBirth
        // 모달 창 띄울 때 입력한 값 보이기 위해
        state.input.englishName = res.englishName
        state.input.gender = res.gender
        state.input.address = res.address
        state.input.dateBirth = res.dateBirth          
      })
      state.displayInfoModal = false
    }

    return {
      state, v$, handleSubmit, submitted, propUpdate
    }
  },

  methods: {
    change(value) {
        console.log(value);
        if(event.target.value === "있음"){
            this.state.Disabled_check = true;
        }
        if(event.target.value === "없음"){
            this.state.Disabled_check = false;
        }
    },

    isRequired(value) {
      return value? true : 'This field is required'
    },

    changeImg() {
      
    },
    goToModify() {
      router.push("Modify")
    },

    openInfoModal() {
      this.state.displayInfoModal = true
    },
    // 병역사항 모달창
    openArmyModal() {
      this.state.displayArmyModal = true
    },
    saveArmyModal() {
      var totalarmy = this.state.checkarmy + "/" + this.state.kind + "/" + this.state.discharge + "/" + this.state.start + " ~ " + this.state.end;
      console.log("병역 컬럼 : " + totalarmy)

      this.$toast.add({severity:'success', summary: '시스템 정보', group: 'center', detail:'병역 사항 등록완료', life: 1000});
      this.state.displayArmyModal = false
    },

    openDisabledModal() {
      this.state.displayDisabledModal = true;
    },

    saveDisabledModal() {
      if(this.state.Disabled_check == false)console.log("없을때 : " + this.state.personalInfo.disabled)
      if(this.state.Disabled_check == true)console.log("설명이 있을때 : " + this.state.personalInfo.disabled + "/" +this.state.description)
      
      this.$toast.add({severity:'success', summary: '시스템 정보', group: 'center', detail:'장애 여부 등록완료', life: 1000});
      this.state.displayDisabledModal = false;
    }
  },
}
</script>

<style lang="scss" scoped>
</style>