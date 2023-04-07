<template>
  <div class="login-form">
    <Panel id="panel" header="🎉축하합니다! 성공적으로 Blocket의 계정을 생성하였습니다.">
      
      <div class="p-grid p-flex-column">
        <div class="p-col-12 information">
          
          Blocket은 블록체인 기술을 기반으로 하는 신상정보 검증 서비스입니다. 
          모든 사용자는 회원가입 후 계정에 할당되는 지갑이 생성됩니다. <br><br>
          생성된 지갑의 주소는 다음과 같습니다.
          <!-- 지갑 주소 -->
          <div class="p-col-12 walletInfo">
            지갑 주소:<br><strong>{{ state.address }}</strong>
          </div>

          <!-- 지갑 개인키 -->
          <div class="p-col-12 walletInfo">
            개인키:<br> <strong>{{ state.privateKey }}</strong>
          </div>
          <br>
          ⚠ 반드시 개인키를 저장하고 있어야 합니다. 개인키는 이후 검증 완료된 사항을 블록체인에 저장하기 위해 필요합니다. 
          <span style="color: #3F72AF; font-weight: bold; cursor: pointer; " @click="saveLocalFile">여기</span>를 눌러 개인키를 별도의 파일로 저장할 수 있습니다. <br>
          ⚠ Blocket 서비스는 개인키를 절대로 데이터베이스에 저장하지 않습니다.
        </div>

        <div class="p-col-12" style="margin: auto; text-align: center;">
          <Button id="login-btn" @keyup.enter="checkValue" @click="checkValue" value="가입하기">로그인하기</Button>          
        </div>

        <div class="p-col-3" style="margin: auto; text-align: center;" @click="goToHome">
          <span style="cursor: pointer;">홈 화면으로</span>
        </div>
      </div>
    </Panel>
  </div>
</template>

<script>
import { reactive } from 'vue'
import router from '../../router'
import * as etherUtils from '@/utils/itemInventory.js'
import { useRoute } from 'vue-router'

export default {
  name: "WalletInfo",
  setup() {
    const route = useRoute()

    const state = reactive({
      address: '',
      privateKey: '',
    })

    // 이 페이지에 진입해서, DB에 지갑 정보를 저장한다.
    const result = etherUtils.createWallet(route.params.email)
    console.log("지갑 저장 결과...", result)
    state.address = result.walletAddress
    state.privateKey = result.privateKey

    // 개인 지갑이 DB에 저장되면, 관리자 계정에서 이더를 전송한다.
    etherUtils.sendEther(state.address)


    return {
      state,
    }
  },
  methods: {
    goToHome() {
      router.push("/")
    },
    saveLocalFile() {
      etherUtils.saveToFile_Chrome("my_private_key" + new Date(), this.state.privateKey)
    },
  },  
}
</script>

<style scoped="scoped">
.walletInfo {
  overflow:hidden; 
  word-wrap:break-word;
  margin-top: 10px;
}
.login-form {
    padding: 0 40px;
    margin-top: 35px;
}
.information {
  padding: 20px;
}
#panel {
    display: block;
    width: 50%;
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
</style>