import logo from './logo.svg';
import './App.css';
import Bar from './components/Bar';
import SignIn from './components/SignIn';
import SignInPage from './pages/SignInPage'


function App() {
  return (
    <div className="App">
      <Bar/>
      
    </div>
  );
}

// App.js


// import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';

// const Home = () => <h2>Home Page</h2>;


// function App() {
//   return (
//     <Router>
//       <nav>
//         <ul>
//           <li>
//             <Link to="/">Home</Link>
//           </li>
//           <li>
//             <Link to="/signin">signin</Link>
//           </li>
//         </ul>
//       </nav>
//       <Routes>
//         <Route path="/" element={<Home />} />
//         <Route path="/signin" element={<SignInPage/>} />
//       </Routes>
//     </Router>
//   );
// }

export default App;



