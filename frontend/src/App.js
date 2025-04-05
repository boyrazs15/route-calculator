import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Locations from './pages/Locations';

function App() {
  return (
      <Router>
        <div className="app-container">
          <aside className="sidebar">
            <ul>
              <li><a href="/locations">Locations</a></li>
            </ul>
          </aside>
          <div className="content">
            <header>
              <h1>Flight Routing App</h1>
            </header>
            <Routes>
              <Route path="/locations" element={<Locations />} />
            </Routes>
          </div>
        </div>
      </Router>
  );
}

export default App;
