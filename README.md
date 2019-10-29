# Media-Loader

The purpose of the library is to abstract the downloading (images, pdf, zip, etc) and caching of remote resources (images, JSON, XML, etc) 
so that client code can easily "swap" a URL for any kind of files ( JSON, XML, etc).

# Usage

<h4> Loading a <code>Bitmap</code> </h4>

    MediaLoader
        .get(context)
        .load(url)
        .into(imageView)
        
    MediaLoader
      .get(context)
      .load(url)
      .into(imageView, callback)


<b>MediaLoader</b> will return a singlton instance that will handle the url and caching throughout your application, the into function will 
either take the target <code>ImageView</code> alone or with an implementation of the  <code>Callback</code> interface to handle the raw <code>Bitmap</code> data <code>onSuccess()</code>,
and in the case of a failure it will pass an Exception to <code>onError()</code>


<h4> Loading a <code>ByteArray</code> </h4>

     MediaLoader
          .get(context)
          .load(url)
          .listen(callback)
          
the <code>listen()</code> function will take a <code>Callback</code> that will have <code>onSuccess()</code> a <code>ByteArray</code>
of the response from request to the <code>url</code>, and in the case of a failure it will pass an Exception to <code>onError()</code>
