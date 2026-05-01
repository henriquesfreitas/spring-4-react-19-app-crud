# React Library - Frontend

This project is the frontend user interface for the library management system. It is a single-page application (SPA) built with React and TypeScript.

## Technology Stack

*   **React:** ^19.2.4 - A JavaScript library for building user interfaces.
*   **React Router:** ^6.23.1 - For declarative routing in the application.
*   **TypeScript:** ~5.9.3 - A typed superset of JavaScript that compiles to plain JavaScript.
*   **Vite:** ^8.0.1 - A modern frontend build tool that provides a faster and leaner development experience.
*   **Tailwind CSS:** ^3.4.4 - A utility-first CSS framework for rapid UI development.
*   **Axios:** ^1.7.2 - For making HTTP requests to the backend API.
*   **StompJS & SockJS:** For real-time communication with the backend via WebSockets.

## Prerequisites

Before you can run this project, you will need to have the following installed:

*   **Node.js** (which includes npm) - It is recommended to use a recent LTS (Long Term Support) version.
*   The backend **Spring API Library** project must be running, as this frontend application makes API calls to it.

## How to Run the Project

### 1. Install Dependencies

First, you need to install the project's dependencies as defined in `package.json`.

Open a terminal or command prompt and navigate to the root directory of the `react-library` project. Then, run the following command:

```sh
npm install
```

This will download all the necessary packages into the `node_modules` directory.

### 2. Start the Development Server

Once the dependencies are installed, you can start the Vite development server.

In the same terminal, run:

```sh
npm run dev
```

This command will start the development server and provide you with a local URL, which is typically `http://localhost:5173`. Open this URL in your web browser to see the application.

The development server features **Hot Module Replacement (HMR)**, which means that when you make changes to the source code, the application will automatically update in the browser without needing a full page reload.

## Security and Login

This application uses JWT-based authentication. Accessing the main book management page requires you to log in.

### Default Login Credentials

The backend is seeded with a default user for development purposes:

*   **Username:** `admin`
*   **Password:** `password`

Use these credentials on the login page to access the application.

## Project Structure

*   `src/api`: Contains the configured Axios client with JWT interceptors.
*   `src/components`: Contains generic, reusable components (`ProtectedRoute`).
*   `src/context`: Contains the `AuthContext` for managing global authentication state.
*   `src/features/book-management`: Contains all components, hooks, and logic related to the book management feature.
*   `src/pages`: Contains top-level page components like `LoginPage`.
*   `src/services`: Contains services for API communication (`AuthService`, `BookService`, `WebSocketService`).
*   `src/types`: Contains the TypeScript type definitions (`Book`, `Page`, etc.) shared across the application.
*   `src/App.tsx`: The main entry point component that handles routing.
