/*
 * RelativeStrengthOfTopPitchClassesFeature.java
 * Version 1.2
 *
 * Last modified on April 11, 2010.
 * McGill University
 */

package jsymbolic.features;

import java.util.LinkedList;
import javax.sound.midi.*;
import ace.datatypes.FeatureDefinition;
import jsymbolic.processing.MIDIIntermediateRepresentations;


/**
 * A feature exractor that finds the frequency of the 2nd most common pitch
 * class divided by the frequency of the most common pitch class.
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class RelativeStrengthOfTopPitchClassesFeature
     extends MIDIFeatureExtractor
{
     /* CONSTRUCTOR ***********************************************************/
     
     
     /**
      * Basic constructor that sets the definition and dependencies (and their
      * offsets) of this feature.
      */
     public RelativeStrengthOfTopPitchClassesFeature()
     {
          String name = "Relative Strength of Top Pitch Classes";
          String description = "The frequency of the 2nd most common pitch class divided by\n" +
               "the frequency of the most common pitch class.";
          boolean is_sequential = true;
          int dimensions = 1;
          definition = new FeatureDefinition( name,
               description,
               is_sequential,
               dimensions );
          
          dependencies = null;
          
          offsets = null;
     }
     
     
     /* PUBLIC METHODS ********************************************************/
     
     
     /**
      * Extracts this feature from the given MIDI sequence given the other
      * feature values.
      *
      * <p>In the case of this feature, the other_feature_values parameters
      * are ignored.
      *
      * @param sequence			The MIDI sequence to extract the feature
      *                                 from.
      * @param sequence_info		Additional data about the MIDI sequence.
      * @param other_feature_values	The values of other features that are
      *					needed to calculate this value. The
      *					order and offsets of these features
      *					must be the same as those returned by
      *					this class's getDependencies and
      *					getDependencyOffsets methods
      *                                 respectively. The first indice indicates
      *                                 the feature/window and the second
      *                                 indicates the value.
      * @return				The extracted feature value(s).
      * @throws Exception		Throws an informative exception if the
      *					feature cannot be calculated.
      */
     public double[] extractFeature( Sequence sequence,
          MIDIIntermediateRepresentations sequence_info,
          double[][] other_feature_values )
          throws Exception
     {
          double value;
          if (sequence_info != null)
          {
               // Find the highest bin
               int max_index = mckay.utilities.staticlibraries.MathAndStatsMethods.getIndexOfLargest(sequence_info.pitch_class_histogram);
               
               // Find the second highest bin
               double second_max = 0;
               int second_max_index = 0;
               for (int bin = 0; bin < sequence_info.pitch_class_histogram.length; bin++)
                    if (sequence_info.pitch_class_histogram[bin] > second_max &&
                    bin != max_index)
                    {
                    second_max = sequence_info.pitch_class_histogram[bin];
                    second_max_index = bin;
                    }
               
               // Calculate the value
               value = sequence_info.pitch_class_histogram[second_max_index] /
                    sequence_info.pitch_class_histogram[max_index];
          }
          else value = -1.0;
          
          double[] result = new double[1];
          result[0] = value;
          return result;
     }
}