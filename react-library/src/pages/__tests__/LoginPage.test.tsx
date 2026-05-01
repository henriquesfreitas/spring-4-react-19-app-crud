import { render, screen, fireEvent } from '@testing-library/react';
import { describe, it, expect, vi } from 'vitest';
import { BrowserRouter as Router } from 'react-router-dom';
import { AuthProvider } from '../../context/AuthContext';
import LoginPage from '../LoginPage';
import '@testing-library/jest-dom'; // Ensure custom matchers are available

// Mock the AuthService to prevent actual API calls during tests
vi.mock('../../services/AuthService', () => ({
  login: vi.fn(),
}));

// A helper function to render the component within necessary providers
const renderLoginPage = () => {
  return render(
    <Router>
      <AuthProvider>
        <LoginPage />
      </AuthProvider>
    </Router>
  );
};

describe('LoginPage', () => {
  it('should render the login form correctly', () => {
    renderLoginPage();

    // Check if the main heading is on the screen
    expect(screen.getByRole('heading', { name: /login/i })).toBeInTheDocument();

    // Check if the input fields are present
    expect(screen.getByLabelText(/username/i)).toBeInTheDocument();
    expect(screen.getByLabelText(/password/i)).toBeInTheDocument();
  });

  it('should allow the user to type into the input fields', async () => {
    renderLoginPage();

    const usernameInput = screen.getByLabelText(/username/i);
    const passwordInput = screen.getByLabelText(/password/i);

    // Simulate user typing
    await fireEvent.change(usernameInput, { target: { value: 'admin' } });
    await fireEvent.change(passwordInput, { target: { value: 'password' } });

    // Assert that the input fields have the typed value
    expect(usernameInput).toHaveValue('admin');
    expect(passwordInput).toHaveValue('password');
  });
});
