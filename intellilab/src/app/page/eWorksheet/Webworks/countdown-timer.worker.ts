/// <reference lib="webworker" />

addEventListener('message', (event) => {
  console.log(`web worker reponses to ${event.data}`)
  if(event.data === 'start timer'){
    let count = 0;

    setInterval(_ => {
      count+=1
      postMessage(count);
    }, 1000);
  }
});


// function countDown() {

//     let count = 0;

//     setInterval(_ => {
//       count+=1
//       postMessage(count);
//     }, 1000);

// }
