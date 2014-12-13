package uk.co.thefishlive.maths.resources.asset;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import uk.co.thefishlive.maths.resources.Resource;
import uk.co.thefishlive.maths.resources.exception.ResourceException;

public class AssetResource implements Resource {
	private final File file;
	private final AssetInfo info;

	public AssetResource(File basedir, AssetInfo info) {
		this.info = info;
		this.file = new File(basedir, info.getLocalPath());
	}

	@Override
	public InputStream getContent() throws ResourceException {
		try {
			return getUrl().openStream();
		} catch (IOException ex) {
			throw new ResourceException(ex);
		}
	}

	@Override
	public String getPath() {
		return info.getPath();
	}

	@Override
	public URL getUrl() throws ResourceException {
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException ex) {
			throw new ResourceException(ex);
		}
	}
}
