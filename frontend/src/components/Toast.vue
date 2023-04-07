<template>
    <div>
        <Toast/>
        <Toast position="center" group="center"/>
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
                            <Button class="p-button-success" label="Yes" @click="onConfirm"></Button>
                        </div>
                        <div class="p-col-6">
                            <Button class="p-button-secondary" label="No" @click="onReject"></Button>
                        </div>
                    </div>
                </div>
            </template>
        </Toast>
    </div>
</template>

<script>
    export default {}
</script>

<style></style>