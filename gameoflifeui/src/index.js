import React from 'react';
import ReactDOM from 'react-dom/client';
import {BrowserRouter as Router} from "react-router-dom";
import HeaderComponent from "./components/Header";
import GameOfLifeUI from "./components/GameOfLifeUI";
import './bootstrap.min.css';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <div>
          <Router>
              <div className="container">
                  <HeaderComponent/>
                  <GameOfLifeUI/>
              </div>
          </Router>
      </div>
  </React.StrictMode>
);

