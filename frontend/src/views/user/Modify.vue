<template>
    <div>
        <Toast position="bottom-center" group="bc">
            <template #message="slotProps">
                <div class="p-d-flex p-flex-column">
                    <div class="p-text-center">
                        <i class="pi pi-exclamation-triangle" style="font-size: 3rem"></i>
                        <h4>{{slotProps.message.summary}}</h4>
                        <p>{{slotProps.message.detail}}</p>
                    </div>
                    <div class="p-grid p-fluid">
                        <div class="p-col-6">
                            <Button id="bt" class="p-button-success" label="Yes" @click="onConfirm"></Button>
                        </div>
                        <div class="p-col-6">
                            <Button id="bt" class="p-button-secondary" label="No" @click="onReject"></Button>
                        </div>
                    </div>
                </div>
            </template>
        </Toast>
    <div style="background-color: #F9F7F7;">
        <div class="login-form">
            <Panel header="Blocket을 체험해보세요." id="panel">
                <div>
                    <label for="username1">이메일</label>
                </div>
                <InputText
                    readonly
                    type="text"
                    id="email"
                    name="email"
                    v-model="state.email"
                    class="InputText"/>
                <!-- <div>
                    <label for="username1">비밀번호</label>
                </div>
                <InputText
                    type="password"
                    id="password"
                    name="password"
                    v-model="password"
                    ref="password"
                    class="InputText"
                    placeholder="6자 이상"/> -->
                <div>
                    <label for="username1">이름</label>
                </div>
                <InputText
                    type="text"
                    id="name"
                    name="name"
                    v-model="state.name"
                    class="InputText"/>
                <div>
                    <label for="username1">소속</label>
                </div>
                <InputText
                    type="text"
                    id="belong"
                    name="belong"
                    v-model="state.belong"
                    class="InputText"/>
                <div>
                    <label for="username1">연락처</label>
                </div>
                <InputText
                    type="text"
                    id="phoneNumber"
                    name="phoneNumber"
                    v-model="state.phoneNumber"
                    maxlength="11"
                    class="InputText"
                    placeholder="-를 제외하고 입력해주세요."
                    />
                <span v-if="show_brn">
                <div>
                    <label for="username1">사업자 등록번호</label>
                </div>
                
                <InputText
                    type="text"
                    id="brn"
                    name="brn"
                    v-model="state.brn"
                    ref="brn"
                    class="InputText"/>
                </span>
                <div>
                    <div class="center-btn">
                        <Button class="p-button-info" @click="modifyUser">
                            수정하기
                        </Button>
                         <Button id="done" class="p-button-danger" @click="deleteUser">
                            탈퇴하기
                        </Button>
                        <div>
                            <div>
                            <span @click="home" class="goback-link">
                                홈으로 돌아가기
                            </span>
                            </div>
                        </div>
                    </div>
                </div>
            </Panel>
        </div>
        </div>
    </div>
</template>
<script>
    // import http from "@/utils/http-common";
    import * as pService from '@/utils/pService.js'
    import { reactive } from 'vue'
    // import axios from 'axios'

    export default {
        name: "signup",
        computed: {},
        setup() {
        //   pService.checkToken()  
          pService.UserCheck().then(res => {  // 각 함수는 비동기 처리하였음
            state.email = res.email
            state.name = res.name
            state.belong = res.belong
            state.phoneNumber = res.phoneNumber
          })

        const state = reactive({
            email :null,
            name : null,
            belong : null,
            phoneNumber : null
        })
    return {
      state,
    }
        },
        methods: {
            modifyUser() {
                let err = true;
                let msg = "";
                if(!this.email){
                    this.$toast.add({severity:'warn', summary: '시스템 정보', group: 'center', detail:'이메일을 입력해주세요.', life: 1000});
                    err = false
                    }
                if(!this.password){
                    this.$toast.add({severity:'warn', summary: '시스템 정보', group: 'center', detail:'비밀번호를 입력해주세요.', life: 1000});
                    err = false
                }
                if(!this.name){
                    this.$toast.add({severity:'warn', summary: '시스템 정보', group: 'center', detail:'이름을 입력해주세요.', life: 1000});
                    err = false    
                }
                if(!this.belong){
                    this.$toast.add({severity:'warn', summary: '시스템 정보', group: 'center', detail:'소속을 입력해주세요.', life: 1000}); 
                    err = false
                }
                if(!this.phoneNumber){
                    this.$toast.add({severity:'warn', summary: '시스템 정보', group: 'center', detail:'연락처를 입력해주세요.', life: 1000}); 
                    err = false
                }
                

                if (!err) console.log(msg);
                else {
                    if (confirm("수정 하시겠습니까?")) {
                        this.$store.dispatch("modify",
                            {   
                                belong: this.belong,
                                brn: 0,
                                name: this.name,
                                phoneNumber: this.phoneNumber,
                                type: 0
                            });
                    }
                }
            },
            deleteUser() {
                if (confirm("정말 탈퇴 하시겠습니까?")) {
                    pService.UserDelete();
                }
            },
            home() {
                this
                    .$router
                    .push("/");
            }
        }
    };
</script>
<style scoped="scoped">
    .p-button {
    margin-right: .5rem;
    margin-left: .5rem;
    }

    .login-form {
        padding: 0 40px;
        margin-top: 35px;
    }

    .login-form div {
        padding: 10px;
    }

    .login-form label {
        color: #888;
    }
    .login-form .checkbox {
        color: black;
    }

    .InputText {
        width: 100%;
        margin-bottom: 20px;
    }

    #panel {
        display: block;
        width: 25%;
        margin: auto;
    }

    #login-btn {
        background-color: #3f72af;
        color: #fff;
    }

    #checkbox {
        padding : 15px;
    }

    .goback-link {
        color: #888;
        cursor: pointer;
    }

    .center-btn {
        text-align: center;
    }
</style>
<style lang="scss" scoped>
#bt {
    min-width: 10rem;
    margin-right: .5rem;
}

@media screen and (max-width: 960px) {
    #bt {
        width: 100%;
        margin-bottom: .5rem;
    }
}
</style>