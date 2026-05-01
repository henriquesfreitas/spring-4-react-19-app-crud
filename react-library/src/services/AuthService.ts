import apiClient from '../api/apiClient';

interface AuthRequest {
    username?: string;
    password?: string;
}

interface AuthResponse {
    token: string;
}

/**
 * Sends a login request to the backend using our configured axios client.
 * @param authRequest - An object containing the username and password.
 * @returns A promise that resolves to the authentication response (containing the JWT).
 */
export const login = async (authRequest: AuthRequest): Promise<AuthResponse> => {
    // The base URL ('/api') is already part of the apiClient, so we just need the specific path.
    const response = await apiClient.post<AuthResponse>('/auth/login', authRequest);
    return response.data;
};
