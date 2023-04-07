import { createApp } from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import PrimeVue from 'primevue/config'
import InputText from 'primevue/inputtext/sfc'
import Button from 'primevue/button'
import SelectButton from 'primevue/selectbutton';
import Dropdown from 'primevue/dropdown';
import AutoComplete from 'primevue/autocomplete';
import Dialog from 'primevue/dialog';
import Panel from 'primevue/panel';
import Textarea from 'primevue/textarea'
import Calendar from 'primevue/calendar'
import Checkbox from 'primevue/checkbox';
import FileUpload from 'primevue/fileupload'
import ToastService from 'primevue/toastservice';
import Toast from 'primevue/toast';
import Toast1 from "@/components/Toast.vue";
import Tooltip from 'primevue/tooltip'

import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import Paginator from 'primevue/paginator';
import Card from 'primevue/card';

import 'primeflex/primeflex.css'
import './assets/public.css'
import 'primevue/resources/primevue.min.css'
import 'primevue/resources/themes/bootstrap4-light-blue/theme.css'
import 'primeicons/primeicons.css'


createApp(App)
const CLOUD_FRONT_DOMAIN_NAME = "d11bfjty6ba1yx.cloudfront.net";
const app = createApp(App)
  .use(PrimeVue)  
  .use(store)
  .use(router)
  .use(ToastService)
  .component("Tooltip", Tooltip)
  .component("Toast1", Toast1)
  .component("Toast", Toast)
  .component("InputText", InputText)
  .component("SelectButton", SelectButton)
  .component("Dropdown", Dropdown)
  .component("AutoComplete", AutoComplete)
  .component("Button", Button)  
  .component("Dialog", Dialog)  
  .component("Panel", Panel)
  .component("Textarea", Textarea)
  .component("Checkbox", Checkbox)
  .component("Calendar", Calendar)
  .component("FileUpload", FileUpload)
  .component("DataTable", DataTable)
  .component("Column", Column)
  .component("Paginator", Paginator)
  .component("Card", Card);
 
  app.config.globalProperties.CLOUD_FRONT= CLOUD_FRONT_DOMAIN_NAME;
  app.mount("#app");
  
