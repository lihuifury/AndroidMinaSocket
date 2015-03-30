package com.handsmap.util.team.encode;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import java.nio.charset.Charset;

public class CharsetDecoder implements ProtocolDecoder
{


	private final static Charset charset = Charset.forName("UTF-8");

	private IoBuffer buff = IoBuffer.allocate(100).setAutoExpand(true);

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception
	{

		while (in.hasRemaining())
		{
			byte b = in.get();
			if (b == '\n')
			{
				buff.flip();
				byte[] bytes = new byte[buff.limit()];
				buff.get(bytes);
				String message = new String(bytes, charset);

				buff = IoBuffer.allocate(100).setAutoExpand(true);

				out.write(message);
			} else
			{
				buff.put(b);
			}
		}
	}

	@Override
	public void dispose(IoSession session) throws Exception
	{
	}

	@Override
	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1) throws Exception
	{

	}
}
