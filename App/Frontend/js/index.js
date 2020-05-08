const Config = {

    connBack:{

        request: new XMLHttpRequest(),

        user:{
            userUrl(nome, senha){
                return `http://localhost:3030/user?nome=${nome}&senha=${senha}`; 
            },

            async userRequest(type, nome, senha){

                console.log('primeiro log')
                Config.connBack.request.open(type, this.userUrl(nome, senha));
                Config.connBack.request.send()
                Config.connBack.request.onload = async()=>{

                    console.log(JSON.parse(Config.connBack.request.response))
                    return await Config.connBack.request.response
                }
            }

        },

        task:{

            getUserTaskUrl(userId){
                return `http://localhost:3030/task?userid=${userId}`;
            },

            postTaskUrl(userId, nome, descricao, status){
                return `http://localhost:3030/task?nome=${nome}&descricao=${descricao}&userid=${userId}&status=${status}`;
            },

            deleteTaskUrl(taskId){
                return `http://localhost:3030/task/${taskId}`; 
            },

            updateTaskUrl(taskId, userId, nome, descricao, status){
                return `http://localhost:3030/task/${taskId}?nome=${nome}&descricao=${descricao}&userid=${userId}&status=${status}`;
            }
        }
    },

    bindEvents:{

    }

}

var i = Math.floor(Math.random()*10) ;
    var array = []
    for(var j = 0; j < i; j ++){
      array.push({index: j, status:"Concluido"});
    }   

    // function search(string){

    //  var stringReg = new RegExp(string)

    //   document.querySelectorAll('.task-nome').forEach((el, index)=>{
    //     if(el.innerHTML.match(stringReg) == null) el.parentElement.style.display = 'none';

    //     else el.parentElement.style.display = '';
    //   })
        
    // }
    // undefined
    // document.querySelector('.input-pesquisa').addEventListener('keyup', (e)=>{
    // this.search(String(document.querySelector('.input-pesquisa').value))
    
    // })

    var app = new Vue({
      el: '#app',
      data: {
        tasks: array,
        status: array,
        addModal: false,
        modalModify: false
      }
    })