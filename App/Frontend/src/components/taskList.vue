<template>
  <section 
    id="tasks"
    :class="{'task-open': isVisible, 'task-close': !isVisible}"
  >
    <div class="container">
      <div class="row">
        <div class="col-sm-12">
            <button
              @click="createTaskModalIsVisible = true"
            >
              <i class="fa fa-plus"></i>
            
              Criar nova tarefa
            </button>

            <button
              @click="$emit('close', false)"
              class="btn-danger"
            >
              <i class="fa fa-plus"></i>
            
              Fechar projeto
            </button>
        </div>
      </div>

      <div 
        class="project"
        v-for="(task, index) in tasks"
        :key="index"
      >
        <div class="row d-flex align-items-center">
          <div class="col-sm-4">
            <div class="current-status">
              {{ task.status }}
            </div>
          </div>
          <div class="col-sm-6">
            {{ task.nome }}
          </div>
          <div class="col-sm-2">
            <div class="projects-icons">
              <i 
                class="fa fa-eye" 
                @click="openViewModal(task)"
              />
              <i 
                class="fa fa-edit" 
                @click="openEditModal(task)"
              />
              <i class="fa fa-remove" 
                @click="deleteTask(task)"
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <edit-task-modal 
      @closeModal="(e) => {editTaskModalIsVisible = e; getTasks()}"
      :isVisible="editTaskModalIsVisible"
      :task="formData"
      :project="project"
    />

    <create-task-modal 
      @closeModal="(e) => {createTaskModalIsVisible = e; getTasks()}"
      :isVisible="createTaskModalIsVisible"
      :project="project"
    />

    <view-task-modal 
      @closeModal="(e) => {viewTaskModalIsVisible = e}"
      :isVisible="viewTaskModalIsVisible"
      :task="formData"
    />
  </section>
</template>

<script>
  import editTaskModal from '@/components/editTaskModal.vue';
  import createTaskModal from '@/components/createTaskModal.vue';
  import viewTaskModal from '@/components/viewTaskModal.vue';
  import axios from 'axios';

  export default {
    components: {
      editTaskModal,
      createTaskModal,
      viewTaskModal
    },

    props: {
      project: {
        type: Object,
        required: true
      }, 

      isVisible: {
        type: Boolean,
        default: false,
      }
    },

    data: () => ({
      tasks: [],
      formData: {},
      createTaskModalIsVisible: false,
      viewTaskModalIsVisible: false,
      editTaskModalIsVisible: false
    }),

    watch: {
      isVisible() {
        this.getTasks()
      },
    },

    mounted() {
      this.tasks = this.project.tasks;
      
    },

    methods: {
      async openEditModal(task) {
        console.log(task)
        this.formData = await task;
        this.editTaskModalIsVisible = true;
        
      },

      async openViewModal(task) {
        console.log(task)
        this.formData = await task;
        this.viewTaskModalIsVisible = true;
      },

      async getTasks() {
        console.log(this.project)
        await fetch('http://localhost:3030/project/tasks',{
          method: 'POST',
          body:JSON.stringify({
          id: this.project.id
        })
        }).then(resp => resp.json())
          .then((data) => {
            console.log(data)
            this.tasks = data;
          }).catch((e) => {
            console.log(e);
          })
      },

      deleteTask(task){
        fetch(`http://localhost:3030/task/${task.id}`)
        .then(() => this.getTasks())
      }

      
    }
  };
</script>