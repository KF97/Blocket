<template>
    <div class="p-col-6 p-offset-3" style="background-color: #F9F7F7;">
        <h2>검증 목록 조회</h2>        

        <div class="status">
            <Dropdown v-model="selectStatus" :options="status" optionLabel="name" placeholder="검증상태" />
            <Button id="sort-btn" class="p-ml-1" @click="clickStatus">검색</Button>
        </div>

        <DataTable v-if="verifications" :value="verifications.content"
        v-model:selection="selectedProduct" @rowUnselect="onRowUnselect" selectionMode="single" dataKey="id"
        @rowSelect="onRowSelect" class="p-m-3">
            <Column field="id" header="검증ID"></Column>
            <Column field="userId" header="사용자번호"></Column>
            <Column field="galleryId" header="파일ID"></Column>
            <Column field="registrationDate" header="등록일"></Column>
            <Column field="currentStatus" header="현재상태"></Column>
            <Column field="reasonsRejection" header="반려사유"></Column>
        </DataTable>
        <div v-if="verifications">
        <Paginator style="background-color: #F9F7F7;" id="paginator" :first="verifications.pageable.offset" :rows="verifications.size" :totalRecords="verifications.totalElements" @page="onPage($event)">
        </Paginator>
        </div>
        <div v-else>
            <h3>데이터가 존재하지 않습니다.</h3>
        </div>
        </div>

</template>
<script>
import { mapGetters } from "vuex";
export default {
    computed: {
    ...mapGetters(["verifications"]),
    ...mapGetters(["user"]),
    },
    data() {
        return {
            selectStatus:null,
            selectedProduct: null,
            req:{
                page:1,
                size:10,
                verified:"",
            },
            status:[
                { name : "승인대기" },
                { name : "승인완료" },
                { name : "거절" }
            ]
        }
    },
    created() {
        if(localStorage.getItem("accessToken") && this.user.type===2){

            this.$store.dispatch("getVerifications", this.req).then((res)=>{
                console.log(res.verificationList);
        });
        } else{
            this.$router.push("/");
            alert("접근권한이 없습니다.");
        }
    },
    methods:{
        onPage(event){
            this.req.page = event.page+1;
            console.log(this.req.page);
            this.$store.dispatch("getVerifications", this.req);
        },
        clickStatus(){
            this.req.verified = this.selectStatus.name;
            this.$store.dispatch("getVerifications", this.req);
        },
        onRowSelect(event){
            this.$router.push("/verification?no=" + event.data.galleryId);
        }
    }
}
</script>
<style>
    #sort-btn {
        background-color: #3F72AF;
    }
    #sort-btn:hover {
        background-color: #212D4E;
    }
</style>