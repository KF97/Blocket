<template>
    <div class="p-grid p-jc-center vertical-container" style="background-color: #F9F7F7;">
        <div  class="p-col-5">
        <Card id ="card" class="box box-stretched">
            <template #content>
                <!-- <img alt="file_img" :src="'https://'+CLOUD_FRONT+'/test.PNG-20213107023156'" style="width: 100%"> -->
                <img alt="file_img" :src="'https://'+CLOUD_FRONT+'/'+file.filePath" style="width: 100%">
            </template>
        </Card>
        </div>
        <div class="p-col-5">
        <Panel id="panel" class="box box-stretched">
            <template #header>
                <div class="header-font">{{file.galleryId}}&nbsp;{{file.title}}</div>
            </template>
            <div v-if="check=='act'">
                <div>
                    <label for="name">활동명</label>
                </div>
                <InputText
                    type="text"
                    name="name"
                    v-model="file.data.name"
                    class="InputText p-m-2" disabled/>

                <div>
                    <label for="activity">활동구분</label>
                </div>
                <InputText
                    type="text"
                    name="activity"
                    v-model="file.data.activity"
                    class="InputText p-m-2" disabled/>
                <div>
                    <label for="activity">기간</label>
                </div>
                <InputText
                    type="text"
                    name="period"
                    v-model="file.data.period"
                    class="InputText p-m-2" disabled/>
                <div>
                    <label for="activity">설명</label>
                </div>
                <InputText
                    type="text"
                    name="description"
                    v-model="file.data.description"
                    class="InputText p-m-2" disabled/>
            </div>

            <div v-else-if="check=='cert'">
                <div>
                    <label for="name">자격명</label>
                </div>
                <InputText
                    type="text"
                    name="name"
                    v-model="file.data.name"
                    class="InputText p-m-2" disabled/>

                <div>
                    <label for="sortation">자격구분</label>
                </div>
                <InputText
                    type="text"
                    name="sortation"
                    v-model="file.data.sortation"
                    class="InputText p-m-2" disabled/>
                <div>
                    <label for="acquisitionDate">기간</label>
                </div>
                <InputText
                    type="text"
                    name="acquisitionDate"
                    v-model="file.data.acquisitionDate"
                    class="InputText p-m-2" disabled/>
                <div>
                    <label for="score">점수/등급</label>
                </div>
                <InputText
                    type="text"
                    name="score"
                    v-model="file.data.score"
                    class="InputText p-m-2" disabled/>
            </div>
            <div v-else-if="check=='edu'">
                <div>
                    <label for="name p-m-2">최종 학교명</label>
                </div>
                <InputText
                    type="text"
                    name="name"
                    v-model="file.data.name"
                    class="InputText p-m-2" disabled/>

                <div>
                    <label for="sortation">학교구분</label>
                </div>
                <InputText
                    type="text"
                    name="sortation"
                    v-model="file.data.sortation"
                    class="InputText p-m-2" disabled/>
                <div>
                    <label for="grades">학점</label>
                </div>
                <InputText
                    type="text"
                    name="grades"
                    v-model="file.data.grades"
                    class="InputText p-m-2" disabled/>
            </div>
            <div>
                    <label for="verified">승인여부</label>
            </div> 
            <InputText
                    type="text"
                    name="verified"
                    v-model="file.currentStatus"
                    class="InputText p-m-2" disabled/>

            <div class="btn">
            <div>
                    <label for="verified">반려사유</label>
            </div>
            <div>
            <InputText
                    type="text"
                    name="resonsRejection"
                    v-model="file.reasonsRejection"
                    :placeholder="`${file.reasonsRejection}`"
                    aria-describedby="resons-rejection"
                    class="InputText p-m-2"/>
            </div>
                <Button label="승인" id="accept-btn" class="p-button-raised p-button-primary p-m-1" @click="accept"/>
                <Button label="거부" class="p-button-raised p-button-danger p-m-1" @click="refuse"/>
            </div>

        </Panel>
    </div>
    </div>
</template>
<script>
import { mapGetters } from "vuex";
import * as etherService from '@/utils/itemInventory.js'
import router from '../../router'

export default {
  computed: {
    ...mapGetters(["file"]),
    ...mapGetters(["user"]),
  },
  data(){
      return{
          check:'',
          rejectCheck:false,
          verify:{
              fileId:0,
              verified:'',
              reasonsRejection:'',
          },
      }
  },
  created() {
       if(localStorage.getItem("accessToken") && this.user.type===2){

        const fileId = this.$route.query.no;
        this.verify.fileId = fileId;
        this.$store.dispatch("getFileInfo", { fileId });

        this.verify.verified = this.file.currentStatus;
        this.check = this.file.sortation;
        console.log("파일 정보는", this.file)
       } else{
           this.$router.push("/");
            alert("접근권한이 없습니다.");
        }
    // console.log(this.CLOUD_FRONT);
  },
  methods: {
      accept(){
          this.verify.verified = "승인완료";
          const fileHash = this.file.title     
          
          this.$store.dispatch("patchVerification", this.verify).then(()=>{
            // 승인 후 트랜잭션 전송한다.
            etherService.saveState(this.file.id, fileHash) // 트랜잭션 전송 후의 트랜잭션 해시이다.
            router.push("/verificationList")
          });
      },
      refuse(){
        this.verify.verified = "거절";
        this.verify.reasonsRejection = this.file.reasonsRejection;
        this.$store.dispatch("patchVerification", this.verify).then(()=>{
        alert("검증이 완료되었습니다.");
        });
      }
  },
}
</script>
<style>
    /* #panel {
        display: block;
        width: 40%;
        margin: auto;
    } */
    .InputText {
        width: 100%;
    }
    #accept-btn {
        background-color: #3F72AF;
    }
    #accept-btn:hover {
        background-color: #212D4E;
    }
    /* #card{
                display: block;
        width: 40%;
        margin: auto;
    } */
</style>