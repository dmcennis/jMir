/*
 * OverallDynamicRangeFeature.java
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
 * A feature exractor that finds the maximum loudness minus the minimum loudness 
 * value.
 *
 * <p>No extracted feature values are stored in objects of this class.
 *
 * @author Cory McKay
 */
public class OverallDynamicRangeFeature
     extends MIDIFeatureExtractor
{
     /* CONSTRUCTOR ***********************************************************/
     
     
     /**
      * Basic constructor that sets the definition and dependencies (and their
      * offsets) of this feature.
      */
     public OverallDynamicRangeFeature()
     {
          String name = "Overall Dynamic Range";
          String description = "The maximum loudness minus the minimum loudness value.";
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
               int max = 0;
               int min = 127;
               for (int i = 0; i < sequence_info.note_loudnesses.length; i++)
                    for (int j = 0; j < sequence_info.note_loudnesses[i].length; j++)
                    {
                    if (sequence_info.note_loudnesses[i][j] > max)
                         max = sequence_info.note_loudnesses[i][j];
                    if (sequence_info.note_loudnesses[i][j] < min)
                         min = sequence_info.note_loudnesses[i][j];
                    }
               
               value = ((double) (max - min));
          }
          else value = -1.0;
          
          double[] result = new double[1];
          result[0] = value;
          return result;
     }
}