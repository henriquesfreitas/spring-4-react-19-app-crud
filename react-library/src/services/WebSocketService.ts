import { Client } from '@stomp/stompjs';
import type { IMessage } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

const SOCKET_URL = import.meta.env.VITE_SOCKET_URL;

let stompClient: Client;

export const connect = (onMessageReceived: (message: any) => void) => {
    stompClient = new Client({
        webSocketFactory: () => new SockJS(SOCKET_URL),
        onConnect: () => {
            console.log('Connected to WebSocket');
            stompClient.subscribe('/topic/book-updates', (message: IMessage) => {
                onMessageReceived(JSON.parse(message.body));
            });
        },
        onStompError: (frame) => {
            console.error('Broker reported error: ' + frame.headers['message']);
            console.error('Additional details: ' + frame.body);
        },
    });

    stompClient.activate();
};

export const disconnect = () => {
    if (stompClient) {
        stompClient.deactivate();
        console.log('Disconnected from WebSocket');
    }
};
