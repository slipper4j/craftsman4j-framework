package com.slipper4j.framework.websocket.core.sender.local;

import com.slipper4j.framework.websocket.core.sender.AbstractWebSocketMessageSender;
import com.slipper4j.framework.websocket.core.sender.WebSocketMessageSender;
import com.slipper4j.framework.websocket.core.session.WebSocketSessionManager;

/**
 * 本地的 {@link WebSocketMessageSender} 实现类
 *
 * 注意：仅仅适合单机场景！！！
 *
 * @author slipper4j
 */
public class LocalWebSocketMessageSender extends AbstractWebSocketMessageSender {

    public LocalWebSocketMessageSender(WebSocketSessionManager sessionManager) {
        super(sessionManager);
    }

}
