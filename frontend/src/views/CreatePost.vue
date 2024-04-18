<template>
  <div class="course-section">
    <form @submit.prevent="createPost">
      <div>
        <label for="title">Title:</label>
        <input type="text" id="title" v-model="post.title" required>
      </div>
      <div>
        <label for="category">Category:</label>
        <input type="text" id="category" v-model="post.category" required>
      </div>
      <div>
        <label for="description">Description:</label>
        <textarea id="description" v-model="post.description" required></textarea>
      </div>
      <div>
        <label for="content">Content:</label>
        <textarea id="content" v-model="post.content" required></textarea>
      </div>
      <div>
        <label for="image">Image URL:</label>
        <input type="text" id="image" v-model="post.image">
      </div>
      <button type="submit">Create Post</button>
    </form>
  </div>
</template>

<script>
import { getAuthenticatedUser } from '@/js/authentication'; // Assuming the correct path
import { getCurrentInstance } from "vue";

export default {
  data() {
    return {
      post: {
        title: '',
        author: '',
        category: '',
        description: '',
        content: '',
        postDate: '',
        image: ''
      }
    };
  },
  methods: {
    createPost() {
      const currentUser = getAuthenticatedUser();
      if (!currentUser) {
        console.error('User not authenticated');
        return;
      }

      this.post.author = currentUser.username;
      this.post.postDate = new Date().toISOString();


      fetch( 'http://localhost:8080/api/community/courses', { // Use environment variables for API_URL
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${currentUser.token}`
        },
        body: JSON.stringify(this.post)
      })
          .then(response => {
            if (!response.ok) {
              throw new Error('Failed to create post');
            }
            console.log('Post created successfully');
            this.resetForm();
          })
          .catch(error => {
            console.error('Error creating post:', error);
          });
    },
    resetForm() {
      this.post = {
        title: '',
        author: '',
        category: '',
        description: '',
        content: '',
        postDate: '',
        image: ''
      };
    }
  }
};
</script>



<style scoped>
.course-section {
  background-color: #EAEAEA;
  margin: 0;
}

</style>