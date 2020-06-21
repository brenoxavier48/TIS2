<template>
  <section id="dashboard">
    <navbar />
    
    <search-dashboard 
      @searchProject='filterProjects'
    />
    
    <div class="container">
      <button
        @click="createProjectModalIsVisible = true"
      >
        <i class="fa fa-plus"></i>
        
        Criar novo projeto
      </button>
    </div>

    <projects-table 
      :projects="projects"
      @getItems="getProjects()"
    />

    <create-project-modal 
      @closeModal="(e) => {createProjectModalIsVisible = e; getProjects()}"
      :isVisible="createProjectModalIsVisible"
    />

  </section>
</template>

<script>
  import axios from 'axios';
  import navbar from '@/components/navbar.vue';
  import searchDashboard from '@/components/search-dashboard.vue';
  import projectsTable from '@/components/projects-table.vue';
  import createProjectModal from '@/components/createProjectModal.vue';

  export default {
    components: {
      navbar,
      searchDashboard,
      projectsTable,
      createProjectModal,
    },

    data: () => ({
      createProjectModalIsVisible: false,
      projects: [],
      allProjects: [],
      // projects: [
      //   {
      //     status: 'concluido',
      //     name: 'Andrey DLC',
      //     description: 'asas',
      //     response: 'asas',
      //     tasks: [
      //       {
      //         name: 'Pegar coco do cachorro', 
      //         status: 'concluido',
      //         description: 'pegarjahdahjs',
      //         response: 'Andrey'
      //       }
      //     ]
      //   },
      //   {
      //     status: 'Ativo',
      //     name: 'Andrey DLC'
      //   },
      //   {
      //     status: 'Ativo',
      //     name: 'Andrey DLC'
      //   }
      // ],
      // allProjects: [
      //   {
      //     status: 'concluido',
      //     name: 'Andrey DLC',
      //     description: 'asas',
      //     response: 'asas',
      //     tasks: [
      //       {
      //         name: 'Pegar coco do cachorro', 
      //         status: 'concluido',
      //         description: 'pegarjahdahjs',
      //         response: 'Andrey'
      //       }
      //     ]
      //   },
      //   {
      //     status: 'Ativo',
      //     name: 'Andrey DLC'
      //   },
      //   {
      //     status: 'Ativo',
      //     name: 'Andrey DLC'
      //   }
      // ]
    }),

    mounted() {
      this.getProjects();
    },

    methods: {
      filterProjects(searchString) {
        let regex = new RegExp(searchString, 'gi');
        this.projects = this.allProjects.filter((e) => {
          return e.nome.match(regex);
        });
      },

      //requisição dados dos projetos
      getProjects() {
        axios.post('http://localhost:3030/user/projects', JSON.stringify({
          id: localStorage.getItem('id')
        }))
          .then(({data}) => {
            this.projects = data;
            this.allProjects = data;
          }).catch((e) => {
            console.log(e);
          })
      },

      
    }
  };
</script>