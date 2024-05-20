<template>
  <div class="course-section">
    <div class="post-form">
      <p>Please ensure that your post adheres to our guidelines.</p>
      <button class="guideline-button" @click="toggleGuidelinesModal">Guidelines</button>
      <p>If you're unsure about any aspect of our guidelines, please take a moment to review them. We appreciate your cooperation in maintaining a positive and constructive community.</p>
    </div>

    <guidelines class="post-form" v-if="showGuidelinesModal" @close="showGuidelinesModal = false" />

    <form @submit.prevent="createPost" class="post-form">
      <div class="form-group">
        <label for="title">Title:</label>
        <input type="text" id="title" v-model="post.title" required maxlength="100">
      </div>
      <div class="form-group">
        <label for="category">Category:</label>
        <input type="text" id="category" v-model="post.category" required maxlength="100">
      </div>
      <div class="form-group">
        <label for="description">Topic:</label>
        <input type="text" id="description" v-model="post.description" required maxlength="100"></input>
      </div>
      <div class="form-group">
        <div class="content-div">
        <label for="content">Content:</label>
          <p id="max-characters">(max characters: 6000)</p>
        </div>
        <textarea class="big-input" id="content" v-model="post.content" required maxlength="6000"></textarea>
        <p class="char-counter">{{ remainingCharacters }}</p>
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
        <input type="text" id="image" v-model="post.image" maxlength="255">
      </div>
      <button type="submit">Create Post</button>
    </form>
  </div>
</template>

<script>
import {getAuthenticatedUser, hasRole, isAuthorized} from '@/js/authentication'; // Assuming the correct path
import Guidelines from "@/components/Guidelines.vue";
import router from "@/router";
import {getCookie} from "@/js/tools";
import {redirectTo} from "@/js/navigation";
import {sendApiRequest} from "@/js/requests";


export default {
  components: {Guidelines},
  mounted() {
    const currentUser = getAuthenticatedUser();
    if (!currentUser) {
      redirectTo('/login');
    }
  },
  data() {
    return {
      showGuidelinesModal: false,
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
  computed: {
    remainingCharacters() {
      return 6000 - this.post.content.length;
    }
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
    toggleGuidelinesModal() {
      this.showGuidelinesModal = !this.showGuidelinesModal;
    },
    createPost() {
      const currentUser = getAuthenticatedUser();
      if (!currentUser) {
        console.error('User not authenticated');
        return;
      }

      this.post.author = currentUser.username;
      this.post.postDate = new Date().toISOString();
      const token = getCookie('jwt');

      fetch( 'https://localhost:8443/api/community/courses', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': 'Bearer ' + token,
        },
        body: JSON.stringify(this.post)
      })
          .then(response => {
            if (!response.ok) {
              console.log(response);
              throw new Error('Failed to create post');
            }
            console.log('Post created successfully');
            alert('Post created successfully!');
            this.resetForm();
            sendApiRequest('GET', '/community/courses', success, error);
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

function success(data) {
  let recentPost = data[data.length - 1];
  redirectTo('/community/post/?id=' + recentPost.courseId);
}

function error() {
  console.log('error');
}
</script>



<style scoped>
.course-section {
  background-color: var(--light-3);
  padding: 50px 0;
}

.post-form {
  max-width: 900px;
  margin: auto auto 50px auto;
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

.guideline-button{
  padding: 0;
  color: var(--base-1);
  background: none;
  border: none;

  &:hover {
    color: var(--base-2);
  }
}

#max-characters {
  font-size: 14px;
  color: var(--dark-1);
}

.content-div {
  display: flex;
  flex-flow: row;
  align-items: flex-end;
}

#max-characters {
  font-size: 10px;
  margin-bottom: 5px;
  padding-left: 5px;
}

.char-counter {
  display: flex;
  font-size: 14px;
  color: var(--dark-1);
}
</style>