export interface NewsDto{

  // News source name (e.g. CNN, Fox News)
  name: string;
  // Title of article
  title: string;
  // Description of article
 description: string;
  // URL of article
  url: string;
  // URL of image
  urlToImage: string;
  //  Shortened article content
  content: string;
  // date published
  publishedAt: string;
}
