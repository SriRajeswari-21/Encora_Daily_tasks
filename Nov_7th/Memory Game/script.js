let emojis = ["❤️","❤️","🍕","🍕","👀","👀","🏆","🏆","😎","😎","🐬","🐬"];
let first=null, second=null, lock=false, time=60, timer;

const gameBoard = document.getElementById("gameBoard");
const timeEl = document.getElementById("time");
const restartBtn = document.getElementById("restartBtn");

function startGame(){
  gameBoard.innerHTML="";
  first=second=null;
  lock=false;
  time=60;
  timeEl.textContent=time;
  clearInterval(timer);
  timer = setInterval(()=>{countdown()},1000);

  // shuffle emojis
  emojis.sort(()=>Math.random()-0.5);

  // create cards
  emojis.forEach((emoji,i)=>{
    let div = document.createElement("div");
    div.className="col-3 card-box";
    div.setAttribute("data-value",emoji);
    div.setAttribute("data-index",i);
    div.textContent="❓";
    div.onclick=()=>flip(div);
    gameBoard.appendChild(div);
  });
}

function flip(card){
  if(lock || card.classList.contains("matched")) return;

  card.textContent = card.getAttribute("data-value");

  if(!first){
    first = card;
  } else {
    second = card;
    lock = true;

    if(first.getAttribute("data-value") === second.getAttribute("data-value")){
      first.classList.add("matched");
      second.classList.add("matched");
      first = second = null;
      lock=false;
      checkWin();
    } else {
      setTimeout(()=>{
        first.textContent="❓";
        second.textContent="❓";
        first=second=null;
        lock=false;
      },600);
    }
  }
}

function countdown(){
  time--;
  timeEl.textContent = time;
  if(time<=0){
    clearInterval(timer);
    alert("Time over! 😢 Try again");
    startGame();
  }
}

function checkWin(){
  const unmatched = document.querySelectorAll(".card-box:not(.matched)");
  if(unmatched.length === 0){
    clearInterval(timer);
    alert("🎉 Congratulations! You matched all cards!");
    startGame();
  }
}

// restart button
restartBtn.addEventListener("click", startGame);

startGame();
