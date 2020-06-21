const Config = {

  init() {
    let user = this.util.userLocalStorage('get')
    if (user != null) {

      console.log(user)
      init.app.user = user;
      init.app.loginPage = false;
      init.app.cadastroPage = false;
      init.app.homePage = true;

      setTimeout(() => {


        console.log(init.app.user)
        this.util.listTask();
        this.util.inserUserName();
      }, 50)
    }
  },

  connBack: {

    request: new XMLHttpRequest(),

    user: {
      userUrl(nome, senha) {
        return `http://localhost:3030/user?nome=${nome}&senha=${senha}`;
      },

      userRequest(type, nome, senha) {

        Config.connBack.request.open(type, this.userUrl(nome, senha));
        Config.connBack.request.send()
      }

    },

    task: {

      getUserTaskUrl(userId) {
        return `http://localhost:3030/task?userid=${userId}`;
      },

      postTaskUrl(userId, nome, descricao, status) {
        return `http://localhost:3030/task?nome=${nome}&descricao=${descricao}&userid=${userId}&status=${status}`;
      },

      deleteTaskUrl(taskId) {
        return `http://localhost:3030/task/${taskId}`;
      },

      updateTaskUrl(taskId, userId, nome, descricao, status) {
        return `http://localhost:3030/task/${taskId}?nome=${nome}&descricao=${descricao}&userid=${userId}&status=${status}`;
      },

      taskRequest(type, userId, taskId, nome, descricao, status) {

        switch (type) {

          case 'get':
            Config.connBack.request.open(type, this.getUserTaskUrl(userId));
            Config.connBack.request.send();

            break;

          case 'post':
            Config.connBack.request.open(type, this.postTaskUrl(userId, nome, descricao, status))
            Config.connBack.request.send();

            break;

          case 'delete':
            Config.connBack.request.open('get', this.deleteTaskUrl(taskId));
            Config.connBack.request.send();

            break;

          case 'update':
            Config.connBack.request.open('post', this.updateTaskUrl(taskId, userId, nome, descricao, status))
            Config.connBack.request.send();

            break;
        }
      }
    }
  },

  bindEvents: {

    bind() {

      app.cadastro = Config.bindEvents.bindCadastro;
      app.login = Config.bindEvents.bindLogin;
      app.search = Config.bindEvents.bindSearch;
      app.addTask = Config.bindEvents.bindAddTask;
      app.deleteTask = Config.bindEvents.bindDeleteTask;
    },

    bindCadastro() {
      let username = document.querySelector('#username-cadastro').value;
      let senha = document.querySelector('#senha-cadastro').value;

      Config.connBack.request.onload = null;

      if (username && senha) {

        Config.connBack.user.userRequest('post', username, senha);
        Config.connBack.request.onload = () => {

          alert('Cadastro realizado com sucesso!');

          init.app.cadastroPage = false;
          init.app.loginPage = true;
        }
      }

      else alert('Preencha os Campos Username e Senha corretamente!');

    },

    bindLogin() {
      let username = document.querySelector('#username-login').value;
      let senha = document.querySelector('#senha-login').value;

      Config.connBack.request.onload = null;

      if (username && senha) {

        Config.connBack.user.userRequest('get', username, senha);
        Config.connBack.request.onload = () => {

          let userData = Config.connBack.request.response;

          if (userData != 'null') {

            Config.util.userLocalStorage('set', Config.connBack.request.response);
            userData = JSON.parse(Config.connBack.request.response);
            init.app.user = userData;

            Config.util.listTask();

            alert('Login realizado com sucesso!');

            init.app.homePage = true;
            init.app.loginPage = false;


            Config.util.inserUserName();

          }
          else alert('Usuário não encontrado!');
        }

      }
      else alert('Preencha os Campos Username e Senha corretamente!');
    },

    bindSignout() {
      init.app.loginPage = true;
      init.app.cadastroPage = false;
      init.app.homePage = false;
      init.app.task = [];
      init.app.user = { id: null, nome: null, senha: null };

      Config.util.userLocalStorage('set', null);
    },

    bindSearch() {

      let pesquisa = String(document.querySelector('.input-pesquisa').value)

      let stringReg = new RegExp(pesquisa.toLowerCase())

      let nomeTask = '';

      document.querySelectorAll('.task-nome').forEach((task, index) => {

        nomeTask = task.innerHTML.toLowerCase()

        if (nomeTask.match(stringReg) == null) task.parentElement.style.display = 'none';

        else task.parentElement.style.display = '';
      })
    },

    bindAddTask() {

      let titulo = document.querySelector('#titulo-modal-add').value;
      let status = document.querySelector('#status-modal-add').value;
      let desc = document.querySelector('#desc-modal-add').value;
      let backgroundColor = Config.util.choseColor(status)

      let objTask = {}

      Config.connBack.request.onload = null;

      if (titulo) {

        Config.connBack.task.taskRequest('post', init.app.user.id, null, titulo, desc, status)
        Config.connBack.request.onload = () => {

          objTask.id = Number(Config.connBack.request.response);
          objTask.nome = titulo;
          objTask.descricao = desc;
          objTask.status = status;
          objTask.statusColor = backgroundColor;
          objTask.user = init.app.user;

          init.app.tasks.push(objTask);
        }

        init.app.addModal = false;
      }

      else alert('Preencha o Campo Título corretamente!');
    },

    bindDeleteTask(taskId) {

      Config.connBack.request.onload = null;
      Config.connBack.task.taskRequest('delete', null, taskId)
      Config.connBack.request.onload = () => {

        init.app.tasks = init.app.tasks.filter(element => element.id != taskId);
      }
    },

    bindUpdateTask() {

      let taskId = document.querySelector('#id-modal-modify').innerHTML;
      let titulo = document.querySelector('#titulo-modal-modify').value;
      let status = document.querySelector('#status-modal-modify').value;
      let desc = document.querySelector('#desc-modal-add').value;
      let backgroundColor = Config.util.choseColor(status)

      let objTask = {}

      Config.connBack.request.onload = null;

      if (titulo) {

        Config.connBack.task.taskRequest('update', init.app.user.id, taskId, titulo, desc, status)

        Config.connBack.request.onload = () => {

          objTask.id = Number(taskId);
          objTask.nome = titulo;
          objTask.descricao = desc;
          objTask.status = status;
          objTask.statusColor = backgroundColor;
          objTask.user = init.app.user;

          init.app.tasks = init.app.tasks.map(element => {

            if (element.id == Number(taskId)) return objTask

            else return element
          });
        }

        init.app.modalModify = false;
      }

      else alert('Preencha o Campo Título corretamente!');
    }

  },

  util: {

    choseColor(status) {
      switch (status) {
        case 'Andamento':
          return 'backgroundColor:#C7CB0F';

        case 'Concluído':
          return 'backgroundColor:#0FCB87';

        case 'Atrasado':
          return 'backgroundColor:#ff0000';

        default:
          console.log('tanto faz');
          break;
      }
    },

    listTask() {
      if (init.app.user.id) {

        Config.connBack.request.onload = null;
        Config.connBack.task.taskRequest('get', init.app.user.id);
        Config.connBack.request.onload = () => {

          listTask = Config.connBack.request.response;
          console.log(listTask)

          if (listTask != '') {

            var listTaskFormat = []
            var backgroundColor = ''

            JSON.parse(listTask).forEach((el, i) => {

              backgroundColor = Config.util.choseColor(el.status);

              listTaskFormat.push({
                id: el.id,
                nome: el.nome,
                descricao: el.descricao,
                status: el.status,
                statusColor: backgroundColor,
                user: el.user
              })              
            })

            init.app.tasks = listTaskFormat;
          }
        }
      }
    },

    inserUserName() {
      setTimeout(() => {

        document.querySelector('.nome-profile').innerHTML = init.app.user.nome;

      }, 50)
    },

    userLocalStorage(mod, user) {

      switch (mod) {
        case 'get':
          return JSON.parse(localStorage.getItem('user'));
        case 'set':
          localStorage.setItem('user', user);
          break;
      }
    },

    openModalModify(taskId, nome, descricao, status) {
      init.app.modalModify = true;

      setTimeout(() => {
        document.querySelector('#titulo-modal-modify').value = nome;
        document.querySelector('#status-modal-modify').value = status;
        document.querySelector('#desc-modal-add').value = descricao;
        document.querySelector('#id-modal-modify').innerHTML = taskId;
      }, 10);
    }
  }
}

const init = {

  app: new Vue({
    el: '#app',
    data: {
      cadastro: Config.bindEvents.bindCadastro,
      login: Config.bindEvents.bindLogin,
      signout: Config.bindEvents.bindSignout,
      search: Config.bindEvents.bindSearch,
      addTask: Config.bindEvents.bindAddTask,
      deleteTask: Config.bindEvents.bindDeleteTask,
      updateTask: Config.bindEvents.bindUpdateTask,
      openModal: Config.util.openModalModify,
      modalModifyTaskAdd: false,
      modalModifyTask: 'Modificar'  ,
      user: { id: null, nome: null, senha: null },
      //adicionei esse objeto currentProject para facilitar na hora de pegar os dados do projeto atual e não ter que ficar
      //passando parametro em função toda hora.
      currentProject: {},
      //recomendo para as tarefas dos projetos seguir mais ou menos esse padrão, onde a uma key subtasks que é um array de objetos 
      //que recebe todas as tarefas
      tasks: [{
        id: 1,
        nome: 'nome',
        descricao: 'assaas',
        status: 'ativo',        
        statusColor: 'background-color: #00ff00',
        user: 'andrey',
        subtasks: [
          {
            id: 1,
            nome: 'nome',
            descricao: 'assaas',
            status: 'ativo',        
            statusColor: 'background-color: #00ff00',
            user: 'andrey',
          }
        ]
      },],
      addModal: false,
      modalModify: false,
      viewTaskData: false,
      loginPage: false,
      cadastroPage: false,
      homePage: true,
      showViewInfoModal: false,
    },

    watch: {
      modalModifyTaskAdd() {
        if (this.modalModifyTaskAdd == true) {
          this.modalModifyTask = 'Adicionar';
        } else {
          this.modalModifyTask = 'Modificar';
        }
      }
    },

    methods: {
      //essa função viewInfo server para abrir o modal (linha 407) e adicionar os dados do projeto que o usuario selecionou
      //a variavel currentProject (linha 413)
      viewInfo(id) {
        this.showViewInfoModal = true;

        let arr = this.tasks.filter((el) => {
          return el.id == id;
        });

        this.currentProject = arr[0];
      },

    },
  }),
}

Config.init()