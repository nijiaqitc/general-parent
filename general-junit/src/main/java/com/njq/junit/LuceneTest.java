package com.njq.junit;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LuceneTest {
    public static void main(String[] args) throws Exception {
//        StandardAnalyzer analyzer = new StandardAnalyzer();
//        Path path = Paths.get("D:\\lucetest", new String[0]);
//        Directory index = FSDirectory.open(path);
//        IndexWriterConfig config = new IndexWriterConfig(analyzer);
////        config.setUseCompoundFile(false);
//        IndexWriter writer = new IndexWriter(index, config);
//        addDoc(writer, "Lucene in Action", "193398817");
//        addDoc(writer, "Lucene for Dummies", "55320055Z");
//        addDoc(writer, "Managing Gigabytes", "55063554A");
//        addDoc(writer, "The Art of Computer Science", "9900333X");
//        addDoc(writer, "调试 测试、测一下", "测123456");
//        addDoc(writer, "abc", "def");
//        addDoc(writer, "文章", "文章");
//        addDoc(writer, "文qqq章", "文qqq章");
//        addDoc(writer, "11", "11");
//        addDoc(writer, "2", "2");
//        addDoc(writer, "33", "33");
//        writer.commit();
//        writer.close();
//        index.close();

//		writer.delete
//        File fileD = new File("E:\\findAllLUCENE");
        //6.file对象通过listFiles得到文件路径下的所有文件
//        File[] files = fileD.listFiles();
//        writer.update
//        writer.deleteAll();

//        writer.deleteDocuments(new Term("title", "文章"));
//        writer.commit();
//        writer.close();
//        index.close();


//        delDoc();
//        addDoc();
//        updateDoc();
        String queryStr = "文章";
        search(queryStr);


//		config.setOpenMode(OpenMode.CREATE);
    }

    private static void updateDoc() throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Path path = Paths.get("D:\\lucetest", new String[0]);
        Directory index = FSDirectory.open(path);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(index, config);

        Document doc = new Document();
        doc.add(new TextField("title", "文章", Field.Store.YES));
        doc.add(new StringField("isbn", "怪怪的", Field.Store.YES));
        doc.add(new TextField("标题", "二二二", Field.Store.YES));
        doc.add(new StringField("征文", "辅导费大幅度发", Field.Store.YES));
        writer.updateDocument(new Term("title", "文"), doc);

        writer.commit();
        writer.close();
        index.close();

    }

    private static void delDoc() throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Path path = Paths.get("D:\\lucetest", new String[0]);
        Directory index = FSDirectory.open(path);
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(index, config);
//        writer.deleteDocuments(new Term("title", "文章"));


        QueryParser queryParser = new QueryParser("title", analyzer);
        Query query = queryParser.parse("文章");
        writer.deleteDocuments(query);

//        QueryBuilder queryBuilder = new QueryBuilder(analyzer);
//        Query query = queryBuilder.createPhraseQuery("content", "junit");
//        writer.deleteDocuments(query);

//彻底清空被标记的文档，这样就不能回滚
//        writer.forceMergeDeletes();
        //回滚操作，必须在同一事物内
//        writer.rollback();
//主动合并段
//        writer.forceMerge(2);
        writer.commit();
        writer.close();
        index.close();
    }


    private static void addDoc() throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Path path = Paths.get("D:\\lucetest", new String[0]);
        Directory index = FSDirectory.open(path);

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        IndexWriter writer = new IndexWriter(index, config);
//		addDoc(writer, "Lucene in Action", "193398817");
//		addDoc(writer, "Lucene for Dummies", "55320055Z");
//		addDoc(writer, "Managing Gigabytes", "55063554A");
//		addDoc(writer, "The Art of Computer Science", "9900333X");
//		addDoc(writer, "调试 测试、测一下", "测123456");
//		addDoc(writer, "abc", "def");
        addDoc(writer, "文章", "文章");

//		writer.delete
        writer.close();
        index.close();


//        File fileD = new File("E:\\findAllLUCENE");
//        File[] files = fileD.listFiles();
    }


    private static void addDoc(IndexWriter writer, String title, String isbn) throws IOException {
        Document doc = new Document();
        doc.add(new TextField("title", title, Field.Store.YES));
        doc.add(new StringField("isbn", isbn, Field.Store.YES));
        doc.add(new TextField("标题", title, Field.Store.YES));
        doc.add(new StringField("征文", isbn, Field.Store.YES));
        writer.addDocument(doc);

    }

    private static void search(String queryStr) throws Exception {
        Path path = Paths.get("D:\\lucetest", new String[0]);
        Directory index = FSDirectory.open(path);
        StandardAnalyzer analyzer = new StandardAnalyzer();
        Query q = new QueryParser("title", analyzer).parse(queryStr);
//        Query q = new QueryParser("title", analyzer).parse("调试 NOT 三");
//        Query q = new QueryParser("title", analyzer).parse("*:*");
//        Query q = new MatchAllDocsQuery();
//        Query q = new MatchNoDocsQuery();
//        ?uc*n
//        Query q = new WildcardQuery(new Term("title", "?uc*e"));
//new MultiRangeQuery.RangeClause(, )
//        Element et = new ElementImpl(, )
//        new RangeQueryBuilder().getQuery()
//        new BytesRef("");
//        k<=搜索值<=managing
//        TermRangeQuery q = new TermRangeQuery("title", new BytesRef("k"), new BytesRef("managing"), true, true);

//        TermRangeQuery q = new TermRangeQuery("title", new BytesRef("11"), new BytesRef("33"), true, true);
//        IndexSortSortedNumericDocValuesRangeQuery.
//        System.out.println("query: " + q.toString());
//        Numeirc
        int hitsPerPage = 10;
        // 2 create index-searcher
        IndexReader reader = DirectoryReader.open(index);
        System.out.println("文档数量：" + reader.numDocs() + "文档最大总数:" + reader.maxDoc() + "被删除数" + reader.numDeletedDocs());


        IndexSearcher searcher = new IndexSearcher(reader);
        // 3 do search
        TopDocs docs = searcher.search(q, hitsPerPage);
        ScoreDoc[] hits = docs.scoreDocs;

        // display results
        System.out.println("found " + hits.length + " results");
        for (ScoreDoc hit : hits) {
            int docId = hit.doc;
            Document doc = searcher.doc(docId);
            System.out.println(docId + " " + doc.get("title") + " - " + doc.get("isbn") + " - " + doc.get("征文"));
        }

        reader.close();

    }
}
