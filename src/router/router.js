import Vue from "vue";
import router from 'vue-router';
import Login from '../page/login.vue'
import ktvmain from '../page/ktvmain.vue'
import  userinfo from '../components/ktvuser/ktvuserinfo.vue'
import userrechange from '../components/ktvuser/ktvuserrechange.vue'
import songlist from '../components/songlist/songlist.vue'
import uploadsong from '../components/songlist/uploadsong.vue'
import roomlist from '../components/roomlist/roomlist.vue'
import testroom from '../components/test/testroom.vue'
import videoroom from '../page/videoroom.vue'
import itemindex from '../components/itemlist/itemindex.vue'
import itemadd from '../components/itemlist/itemadd.vue'
import itemconsume from '../components/itemlist/itemconsume.vue'
import itemtype from '../components/itemlist/itemtype.vue'
Vue.use(router);
export default new router({
    routes:[
        {
            path:'/',
            redirect:'/login'
        },
    {
        path:'/login',
        component:Login
    },
    {
        path:'/videoroom',
        component:videoroom
    },
    {
        path:'/ktvmain',
        component:ktvmain,
        children:[
            {
                    path:'/userinfo',
                    component:userinfo
            },
            {
                path:'/userrechange',
                component:userrechange
            },
            {
                path:'/songlist',
                component:songlist
            },
            {
                path:'/uploadsong',
                component:uploadsong
            },
            {
                path:'/roomlist',
                component:roomlist
            },
            {
                path:'/testroom',
                component:testroom
            },
            {
                path:'/itemindex',
                component:itemindex
            },
            {
                path:'/itemadd',
                component:itemadd
            },
            {
                path:'/itemconsume',
                component:itemconsume
            },
            {
                path:'/itemtype',
                component:itemtype
            }
        ]
    }

]
})