/*reference to controller*/
var messageApi = Vue.resource('/message{/id}');

/*add form for adding new message*/
Vue.component('message-form',{
    props:['messages'],
    data:function (){
      return{
          text:''
      }
    },
    template:
    '<div>' +
       '<input type="text" placeholder="Write something"/>'+
       '<input type="button" value="save" @click="save"/>'+
       '</div>',
    methods: {
        save:function (){
            var message ={text: this.text};
            messageApi.save({},message).then(result=>
            result.json().then(data=>{
                this.messages.push(data);
            })
            );
        }
    }
});

/* descripe how to print one row from message */
Vue.component('message-row',{
    props:['message'],
    template:'<div><i>({{message.id }})</i>{{message.text}}</div>'
});

/* describe hot to print our messages*/
Vue.component('messages-list',{
    props:['messages'],
    template:
        '<div>' +
            '<message-form :messages="messages"/>'+
            '<message-row v-for="message in messages" :key="message.id" :message="message"/>' +
        '</div>',
    created: function (){
        messageApi.get().then(result =>
            result.json().then(data =>
                data.forEach(message =>this.messages.push(message))
            ))
    }
});

/*print messages*/
var app = new Vue({
    el: '#app',
    template:'<messages-list :messages="messages"/>',
    data:{
        messages: []
    }
})