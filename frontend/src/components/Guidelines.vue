<template>
  <div>
    <h2>Guidelines</h2>
    <ul>
      <li class="listItem" v-for="(guideline, index) in guidelines" :key="index">{{ guideline }}</li>
    </ul>
  </div>
</template>

<script setup>
//Some guidelines generated by Github CoPilot
const guidelines = [
  'Original Content: Ensure that all content posted on the website is original and not plagiarized from other sources. Respect copyright laws and give credit where it\'s due.',
  'Accuracy: Strive for accuracy in all content posted. Verify facts and cite reputable sources when necessary. Avoid spreading misinformation or false information.',
  'Respectful Language: Use respectful language at all times. Avoid offensive or inflammatory language that could harm or offend others. Foster a positive and inclusive online community.',
  'No Hate Speech or Discrimination: Do not tolerate hate speech, discrimination, or harassment of any kind. Respect the diversity of opinions and backgrounds within the community.',
  'Privacy and Consent: Respect the privacy and consent of individuals featured in the content. Obtain proper permissions before using personal information or images of others.',
  'Quality and Relevance: Ensure that all content posted is of high quality and relevant to the website\'s theme or purpose. Avoid irrelevant or low-quality content that may detract from the user experience.',
  'Engagement and Interaction: Encourage engagement and interaction with the audience. Respond to comments and messages in a timely and respectful manner. Foster a sense of community and dialogue.',
  'Transparency: Be transparent about any affiliations, sponsorships, or conflicts of interest related to the content posted. Disclose any potential biases to maintain trust with the audience.',
  'Legal Compliance: Ensure that all content posted complies with applicable laws and regulations, including but not limited to copyright, defamation, and privacy laws. Seek legal advice if unsure about any legal issues.',
  'Regular Updates: Keep the website updated with fresh and relevant content on a regular basis. Maintain consistency in posting frequency to keep the audience engaged.'
]
</script>

<style scoped>
.listItem {
  margin-bottom: 40px;
}
</style>
