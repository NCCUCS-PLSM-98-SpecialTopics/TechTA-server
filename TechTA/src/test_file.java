@Path("/file")
public class test_file {

  private final BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  private final BlobInfoFactory blobInfoFactory = new BlobInfoFactory();
  
  /* step 1. get a unique url */
  
  @GET
  @Path("/url")
  public Response getCallbackUrl() {
    /* this is /_ah/upload and it redirects to its given path */
    String url = blobstoreService.createUploadUrl("/rest/file");
    return Response.ok(new FileUrl(url)).build();
  }
  
  /* step 2. post a file */
  
  @POST
  @Consumes(MediaType.MULTIPART_FORM_DATA)
  public void post(@Context HttpServletRequest req, @Context HttpServletResponse res) throws IOException, URISyntaxException {
    Map<String, BlobKey> blobs = blobstoreService.getUploadedBlobs(req);
    BlobKey blobKey = blobs.get("files[]");
    res.sendRedirect("/rest/file/" + blobKey.getKeyString() + "/meta");
  }
  
  /* step 3. redirected to the meta info */
  
   @GET
    @Path("/{key}/meta")
    public Response redirect(@PathParam("key") String key) throws IOException {
      BlobKey blobKey = new BlobKey(key);
      BlobInfo info = blobInfoFactory.loadBlobInfo(blobKey);

      String name = info.getFilename();
      long size = info.getSize();
      String url = "/rest/file/" + key; 
      FileMeta meta = new FileMeta(name, size, url);

      List<FileMeta> metas = Lists.newArrayList(meta);
      Entity entity = new Entity(metas);
      return Response.ok(entity,MediaType.APPLICATION_JSON).build();
    }

  /* step 4. download the file */
  
  @GET
  @Path("/{key}")
  public Response serve(@PathParam("key") String key, @Context HttpServletResponse response) throws IOException {
    BlobKey blobKey = new BlobKey(key);
    final BlobInfo blobInfo = blobInfoFactory.loadBlobInfo(blobKey);
    response.setHeader("Content-Disposition", "attachment; filename=" + blobInfo.getFilename());
    BlobstoreServiceFactory.getBlobstoreService().serve(blobKey, response);
    return Response.ok().build();
  }
  
  /* step 5. delete the file */
  
  @DELETE
  @Path("/{key}")
  public Response delete(@PathParam("key") String key) {
    Status status;
    try {
      blobstoreService.delete(new BlobKey(key));
      status = Status.OK;
    } catch (BlobstoreFailureException bfe) {
      status = Status.NOT_FOUND;
    }
    return Response.status(status).build();
  }
}