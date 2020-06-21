<template>
  <div class="projects-table">
    <div class="container">
      <div 
        class="project"
        v-for="(project, index) in projects"
        :key="index"
      >
        <div class="row d-flex align-items-center">
          <div class="col-sm-4">
            <div class="current-status">
              {{ project.status }}
            </div>
          </div>
          <div class="col-sm-6">
            <strong>{{ project.nome }}</strong>
          </div>
          <div class="col-sm-2">
            <div class="projects-icons">
              <i 
                class="fa fa-eye" 
                @click="openTaskListScreen(project)"
              />

              <i 
                class="fa fa-edit" 
                @click="openEditModal(project)"
              />
              <i 
                class="fa fa-remove" 
                @click="deleteProject(project)"
              />
            </div>
          </div>
        </div>
      </div>
    </div>

    <task-list 
      :isVisible="openTaskList"
      :project="projectTasks"
      @close="(e) => openTaskList = e"
    />
    
    <edit-project-modal 
      @closeModal="(e) => {editProjectModalIsVisible = e; getProjects()}"
      :isVisible="editProjectModalIsVisible"
      :project="formData"
    />
  </div>
</template>

<script>
  import editProjectModal from '@/components/editProjectModal.vue';
  import taskList from '@/components/taskList.vue';
  import axios from 'axios';

  export default {
    components: {
      editProjectModal,
      taskList
    },

    props: {
      projects: {
        type: Array,
        required: true
      }
    },

    data: () => ({
       editProjectModalIsVisible: false,
       openTaskList: false,
       formData: {},
       projectTasks: {}
    }),

    methods: {
      openEditModal(project) {
        this.formData = project;
        this.editProjectModalIsVisible = true;
      },

      openTaskListScreen(project) {
        this.projectTasks = project;
        this.openTaskList = true;
      },

      getProjects() {
        this.$emit('getItems', true);
      },

      deleteProject(project) {
        axios.get(`http://localhost:3030/projectdelete/${project.id}`)
          .then(({data}) => {
            if (data.success) {
              this.getProjects();
            }
          }).catch((e) => {
            console.log(e)
          })
      }
    }
  };
</script>