function loadNews() {

    let articleContainer = document.getElementById('news-article-container');

    articleContainer.innerHTML = '';

    fetch('http://localhost:8080/api/news')
        .then(response => response.json())
        .then(json => json.forEach(news => {

            let article = document.createElement("article");
            article.style.flex = 'flex: 1 1 15%';

            let h4 = document.createElement("h4");
            h4.classList.add('article-title');
            h4.style.fontSize = '18px';

            let a = document.createElement('a');
            a.setAttribute('href', news.newsUrl);
            a.setAttribute('target', '_blank');

            let pPublished = document.createElement('p');
            pPublished.classList.add('posted-by', 'pt-2');
            pPublished.style.fontSize = '12px';

            let divContent = document.createElement('div')
            divContent.classList.add('article-content');

            let divText = document.createElement('div')
            divText.classList.add('article-text')

            let pText = document.createElement('p');

            let aReadMore = document.createElement('a');
            aReadMore.setAttribute('href', news.newsUrl);
            aReadMore.setAttribute('target', '_blank');
            aReadMore.textContent = 'Read More.';
            aReadMore.style.fontSize = '12px';

            a.textContent = news.title;
            pPublished.textContent = 'Published on: ' + news.createdOn.replace('T', " at ");
            pText.textContent = news.description
                .trim()
                .concat(news.description.charAt(news.description.length - 1) === '.' ? '..' : '...');
            pText.appendChild(aReadMore);
            divText.appendChild(pText);
            divContent.appendChild(divText);
            h4.appendChild(a);
            article.appendChild(h4);
            article.appendChild(pPublished);
            article.appendChild(divContent);

            articleContainer.appendChild(article);
        }));
}

loadNews();