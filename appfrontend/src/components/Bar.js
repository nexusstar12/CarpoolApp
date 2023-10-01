import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import SignInPage from '../pages/SignInPage';
import SignUp from './SignUp';




export default function Bar() {
  return (
    <Router>
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <IconButton
            size="large"
            edge="start"
            color="inherit"
            aria-label="menu"
            sx={{ mr: 2 }}
          >
            <MenuIcon />
          </IconButton>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1 }}>
            Pool
          </Typography>
          
            <Link to="/signup">
              <Button color="inherit">Signup</Button>
            </Link>
            <Link to="/signin">
              <Button color="inherit">Login</Button>
            </Link>
            
          
        </Toolbar>
      </AppBar>
    </Box>
    <Routes>
              <Route path="/signup" element={<SignUp/>} />
              <Route path="/signin" element={<SignInPage/>} />
      </Routes>
    </Router>
  );
}
