<template>
  <h1>ETH 트랜잭션</h1>

  <h1>1. 지갑 생성하기</h1>
  <div class="p-grid">
    
    <Button label="Submit" @click="createWallet" />
    <label for="walletAddress" class="for">지갑 주소</label>
    <InputText id="walletAddress" class="p-col-12" v-model="state.walletAddress" />
    <label for="privateKey" class="for">개인키</label>
    <InputText id="privateKey" class="p-col-12" v-model="state.privateKey" />
  </div>
  

  
  <div class="p-grid">
    <h1>2. 생성한 지갑 정보 DB에 저장하기</h1>
    <Button label="DB에 저장하기" @click="saveWalletInDB" />
  </div>

  <h4>관리자 지갑 : 0xf255FC9eF3778E688950649547D398B027D8b999</h4>
  <!-- <h4>개인키 : 0xb35023e44ad462879d110e4f68c8e794e0097c475d4639d4b9dbf463dcb1ef09</h4> -->
  <div class="p-grid">
    <h1>3. 관리자가 보유한 이더를 새롭게 생성한 사용자 계정 지갑에 넣어주기</h1>
    <Button label="Submit" @click="sendEther" />
  </div>

  <div class="p-grid">
    <h1>4. 데이터 저장 트랜잭션 전송</h1>
    <Button label="Submit" @click="saveState" />
  </div>
  

</template>

<script>
import { createWeb3, sendEther, saveState } from '@/utils/itemInventory.js'
import { reactive } from '@vue/reactivity'
import defaultImage from "~/images/test.png"
import defaultUserImage from "~/images/user.png"

// 0xf255FC9eF3778E688950649547D398B027D8b999 : 관리자 지갑
// 0xb35023e44ad462879d110e4f68c8e794e0097c475d4639d4b9dbf463dcb1ef09 : 관리자 개인키


// 0x41278A913Ae5D0F68CF2D06A4007d76AF696B255 : 사용자 지갑 주소
// 0x2d962412be38ba3b8268c0280c62bcd25cb8df31599d92c84e69459b621330cc : 사용자 개인키

export default {
name: 'test',
components: {},
setup() {
  const web3 = createWeb3()

  const state = reactive({
    web3: web3,
    walletAddress: null,
    privateKey: null,
    defaultImage: defaultImage,
    defaultUserImage: defaultUserImage,
  })
  return {
    state
  }
},

methods: {
  createWallet() {
    console.log("지갑 생성합니다.")
    const myWallet = this.state.web3.eth.accounts.wallet.create(1)
    console.log("생성한 지갑:", myWallet)
    
    this.state.walletAddress = myWallet[0].address
    this.state.privateKey = myWallet[0].privateKey

    // 이제 생성한 지갑의 정보를 infura의 퍼블릭 노드에 저장하자.

  },
  saveWalletInDB() {},
  sendEther() {
    sendEther()
  },
  saveState() {
    saveState()
  }
}

}
</script>

<style lang="scss" scoped> 
</style>