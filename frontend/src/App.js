import React from 'react';
import { BrowserRouter as Router, Routes, Route, NavLink } from 'react-router-dom';
import Locations from './pages/Locations';
import Transportations from './pages/Transportations';
import RoutesPage from './pages/Routes';
import './App.css';
import { ToastContainer } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

function App() {
  return (
      <Router>
        <div className="app-container">
          <aside className="sidebar">
            <ul>
              <li><NavLink to="/locations" activeClassName="active">Locations</NavLink></li>
              <li><NavLink to="/transportations" activeClassName="active">Transportations</NavLink></li>
              <li><NavLink to="/routes" activeClassName="active">Routes</NavLink></li>
            </ul>
          </aside>
          <div className="content">
            <header>Flight Routing App</header>
            <Routes>
              <Route path="/locations" element={<Locations />} />
              <Route path="/transportations" element={<Transportations />} />
              <Route path="/routes" element={<RoutesPage />} />
            </Routes>
          </div>
        </div>
      </Router>
  );
}

export default App;
