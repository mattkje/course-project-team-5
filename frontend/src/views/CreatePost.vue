<template>
  <div class="course-section">
    <form @submit.prevent="createPost" class="post-form">
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" id="title" v-model="post.title" required>
      </div>
      <div class="form-group">
        <label for="category">Category:</label>
        <input type="text" id="category" v-model="post.category" required>
      </div>
      <div class="form-group">
        <label for="description">Topic:</label>
        <input type="text" id="description" v-model="post.description" required></input>
      </div>
      <div class="form-group">
        <label for="content">Content:</label>
        <textarea class="big-input" id="content" v-model="post.content" required></textarea>
        <div class="formatting-tools">
          <button type="button" @click="insertText('# ', 'content')">Heading 1</button>
          <button type="button" @click="insertText('## ', 'content')">Heading 2</button>
          <button type="button" @click="insertText('### ', 'content')">Heading 3</button>
          <button type="button" @click="insertText('**', 'content')">Bold</button>
          <button type="button" @click="insertText('*', 'content')">Italic</button>
          <button type="button" @click="insertText('```', 'content')">Code Block</button>
        </div>
      </div>
      <div class="form-group">
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
    insertText(text, field) {
      const textarea = document.getElementById(field);
      const startPos = textarea.selectionStart;
      const endPos = textarea.selectionEnd;
      const textBefore = textarea.value.substring(0, startPos);
      const textAfter = textarea.value.substring(endPos, textarea.value.length);
      if (text.includes("#")) {
        textarea.value = textBefore + text + textarea.value.substring(startPos, endPos);
      } else {
        textarea.value = textBefore + text + textarea.value.substring(startPos, endPos) + text + textAfter;
      }
      textarea.focus();
      textarea.setSelectionRange(startPos + text.length, endPos + text.length);
    },
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
  padding: 50px 0;
}

.post-form {
  max-width: 1300px;
  margin: auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 5px;
}

.form-group {
  margin-bottom: 20px;
}

.big-input {
  height: 600px;
  margin-bottom: 20px;
}

label {
  display: block;
  margin-bottom: 5px;
}

input[type="text"],
textarea {
  width: 100%;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
}

button[type="submit"] {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: var(--base-1);
  color: #fff;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  cursor: pointer;
}

button[type="submit"]:hover {
  background-color: var(--base-2);
}

.formatting-tools {
  margin-top: 10px;
}

.formatting-tools button {
  margin-right: 5px;
  padding: 5px 10px;
  background-color: #ffffff;
  box-shadow: none;
  border-radius: 5px;
  font-size: 14px;
  cursor: pointer;
}

.formatting-tools button:hover {
  background-color: #e0e0e0;
}

</style>