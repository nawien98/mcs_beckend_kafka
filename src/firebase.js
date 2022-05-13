import firebase from "firebase/compat/app"
import "firebase/compat/auth"

const app = firebase.initializeApp({

    apiKey: "AIzaSyA9Pt6tDyyJQ1PesWMpXHb-RiXpKXEli5g",
  authDomain: "ms-accelerator-59e0b.firebaseapp.com",
  databaseURL: "https://ms-accelerator-59e0b-default-rtdb.firebaseio.com",
  projectId: "ms-accelerator-59e0b",
  storageBucket: "ms-accelerator-59e0b.appspot.com",
  messagingSenderId: "122258930472",
  appId: "1:122258930472:web:85c2b046a78c295601ae1d"
})

export const auth = app.auth()
export default app