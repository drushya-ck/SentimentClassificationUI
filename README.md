# SentimentClassificationUI
Java application for sentiment classification of movie review comments by Naive Bayesian Model!

This is what I do for my master degree of computer science.

To classify the sentiment of movie review into positive or negative, it performs training and classification by Bernoulli Naive Bayes. Before training and classification, certain preprocessing steps are done such as POS tagging using Stanford POS tagger, feature extraction based on POS (Adj, Adv, Verb, and Noun), feature selection by Information Gain on features using set-of-words model. From these gains, some features are selected and ready for Naive Bayes classification.

