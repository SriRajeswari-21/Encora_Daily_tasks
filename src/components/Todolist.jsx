import React,{useState} from 'react'

const Todolist = () => {
    let  t=5;
    const [count,setCount]=useState(0);
    const [input,setInput]=useState("")
    const array=[1,"ads",3,4]
    const handleClick= ()=>{
      t++;
      setCount(count+1);

      console.log("Add tasks",t);
      console.log("Add counter",count);
    }
    const handlechange=(event)=>{
      setInput(event.target.value)
      console.log(input)
    }
  return (
    <div>
     <h1>result: {t===0?"no ":`tasks:${t}`}</h1>
     <h1>result: {t===0?"no ":`counter:${count}`}</h1>
      <p>dxfcgvhbjknnvbbnm,</p>
      <button onClick={handleClick} value='task button' style={{backgroundColor:'red'}}>click</button>
      <input type="text" onChange={handlechange}></input>
      <h1>{input}</h1>
      <ul>
        {array.map(o=><li>{o}</li>)}
      </ul>
    </div>
  )
}

export default Todolist
