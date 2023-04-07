<template>
    <div style="background-color: #F9F7F7;">
        <div class="login-form">
            <Panel header="Blocket을 체험해보세요." id="panel">
                <div>
                    <label for="username1">이메일</label>
                </div>
                <InputText
                    type="text"
                    id="email"
                    name="email"
                    v-model="email"
                    ref="email"
                    class="InputText"
                    style="width:70%"
                    />
                    <Button style="margin-left : 8px;" @click="check">중복체크</Button>
                <div>
                    <label for="username1">비밀번호</label>
                </div>
                <InputText
                    type="password"
                    id="password"
                    name="password"
                    maxlength="12"
                    v-model="password"
                    ref="password"
                    class="InputText"
                    placeholder="6자 이상"/>
                <div>
                    <label for="username1">이름</label>
                </div>
                <InputText
                    type="text"
                    id="name"
                    maxlength="8"
                    name="name"
                    v-model="name"
                    ref="name"
                    class="InputText"/>
                <div>
                <span class="center-btn">
                    <div>
                <input type="checkbox" id="checkbox" v-model="solo" @click="Solo">
                    <label for="checkbox" class="checkbox">개인</label>
                <input type="checkbox" id="checkbox" v-model="company" @click="Company">
                    <label for="checkbox" class="checkbox">기업</label>
                    </div>
                </span>
                </div>
                <div>
                    <label for="username1">소속</label>
                </div>
                <InputText
                    type="text"
                    id="belong"
                    name="belong"
                    v-model="belong"
                    ref="belong"
                    class="InputText"/>
                <div>
                    <label for="username1">연락처</label>
                </div>
                <InputText
                    type="text"
                    id="phoneNumber"
                    name="phoneNumber"
                    v-model="phoneNumber"
                    maxlength="11"
                    ref="phoneNumber"
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
                    v-model="brn"
                    ref="brn"
                    class="InputText"/>
                </span>
                <div>
                    <div class="center-btn">
                        <Button id="login-btn" @keyup.enter="checkValue" @click="checkValue">
                            가입하기
                        </Button>
                        <div>
                            <span @click="home" class="goback-link">
                                홈으로 돌아가기
                            </span>
                        </div>
                    </div>
                </div>
            </Panel>

        </div>
    </div>
</template>
<script>
    import http from "@/utils/http-common";
    import axios from 'axios'
    import store from '../../store'
    import router from '../../router'

    export default {
        name: "signup",
        computed: {},
        data() {
            return {
                show_brn : "",
                solo: true,
                company : false,
                email: "",
                password: "",
                name: "",
                phoneNumber: "",
                belong: "",
                brn: "",
                type: "",
                withdrawal: "",
                id: ""
            };
        },
        methods: {
            Solo() {
                this.solo = true;
                this.company = false;
                this.type = "회원"
                this.show_brn = false;
            },
            Company() {
                this.solo = false;
                this.company = true;
                this.type = "기업"
                this.show_brn = true;
            },
            check() {
                this.$store.dispatch("checkEmail", { email: this.email });
                setTimeout(() => {
                    if(this.$store.state.user.check == 200)this.$toast.add({severity:'success', summary: '시스템 정보', group: 'center', detail:'회원가입 가능한 아이디입니다.', life: 1000})
                    if(this.$store.state.user.check == 409)this.$toast.add({severity:'error', summary: '시스템 정보', group: 'center', detail:'이미 존재하는 사용자 아이디입니다.', life: 1000})
                }, 500);
                
            },
            checkValue() {
                // 사용자 입력값 체크하기
                let err = true;
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
                
                if (!err) 
                    console.log("빈칸을 채워주세요")
                else 
                    this.insertUser();
                }
            ,
            insertUser() {
                http
                    .post("/api/recruit/users/register", {
                        id: 0,
                        belong: this.belong,
                        brn: this.brn,
                        email: this.email,
                        name: this.name,
                        password: this.password,
                        phoneNumber: this.phoneNumber,
                        type: 0,
                        withdrawal: 0
                    })
                    .then(({data}) => {
                        let msg = "회원가입 실패!!";
                        if (data.statusCode == 200) {
                            msg = "회원가입 완료";
                            
                            // 이메일을 vuex에 설정한다. 
                            store.dispatch("setUserEmail", this.email)

                            axios({
                                url: 'http://3.34.191.232:8000/api/recruit/personalinfo',
                                method: 'POST',
                                headers: {
                                    // 회원가입 로직이므로 토큰이 없다.
                                    'Content-Type': 'application/json'
                                },
                                data: {
                                    'email': this.$store.state.user.userEmail
                                }
                            })
                            // router.push({name: "WalletInfo", params: { email: this.email }});
                            router.push("/login")
                        }
                        console.log(msg);
                    })
                    .catch((error) => {
                        alert("회원가입 실패");
                        console.dir(error);
                    });
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

    #login-btn:hover {
        background-color: #f9f7f7;
        color: #112d4e;
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