<template>
<div style="background-color: #F9F7F7;">
   <div id="main">
            <div class="p-fluid p-formgrid p-grid">
                <div class="p-field p-col-7">
                   <img :src="require(`@/assets/images/left.png`)"/>
                </div>
                <div class="p-field p-col-5">
                   <br/>
                   <h1>Blocket이란?</h1>
                   <p>1. 자격증, 성적증명서 등 채용 과정에서</p>
                   <p>필요한 개인 정보를 서비스 내 제출</p>
                   <p>2. 관리자가 데이터 검증 후 블록체인에</p>
                   <p>해시값으로 저장</p>
                   <p>3. 기업들은 서비스의 지원자 계정을 통해</p>
                   <p>블록체인 내에서 정보를 손쉽게 조회</p>
                </div>
            </div>
        </div>
</div>
</template>

<script>
export default {
  props: {
    msg: String,
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.row div{
  display: inline;
}

#main {
  margin-top: 5%;
}

#main img {
  margin-left: 30%;
  margin-top : 5%;
  width: 50%;
}

</style>
